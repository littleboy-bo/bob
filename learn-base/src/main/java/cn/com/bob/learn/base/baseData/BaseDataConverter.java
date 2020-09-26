package cn.com.bob.learn.base.baseData;

import cn.com.bob.learn.base.model.BaseData;
import cn.com.bob.learn.base.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于将{@link BaseData}转化为其他不同的数据类型
 * <p>
 * 同时提供方法，通过反射的机制给对象中设置值。
 *
 * @author songbo
 * @version 2.0.0
 * @since 2.0.0
 */
public final class BaseDataConverter {

  private static final String HORIZONTAL_LINE_PLACEHOLDERS = "-";
  private static final BaseDataConverter CONVERTER = new BaseDataConverter();
  private final Map<String, Map<String, PropertyDescriptor>> descriptorCache = new ConcurrentHashMap<>();

  private BaseDataConverter() {

  }

  public static BaseDataConverter getInstance() {
    return CONVERTER;
  }

  /**
   * 读取对象{@code t}的指定属性{@code field}的值
   *
   * @param field 属性名称
   * @param t 数据对象
   * @return 获取到指定属性的值
   */
  public Object getValue(String field, Object t) {
    if (field == null || t == null) {
      return null;
    }

    String beanName = t.getClass().getName();
    String cacheKey = beanName + HORIZONTAL_LINE_PLACEHOLDERS + field;

    synchronized (this) {
      int index = field.indexOf(".");

      if (index != -1) {
        cacheKey = beanName + HORIZONTAL_LINE_PLACEHOLDERS + field.substring(0, index);
        return getValue(field.substring(index + 1), readValueFromDescriptor(cacheKey, t));
      }
      return readValueFromDescriptor(cacheKey, t);
    }
  }

  private Object readValueFromDescriptor(String field, Object t) {
    PropertyDescriptor descriptor = getDescriptorFromCache(field, t);
    return descriptor == null ? null : readValue(descriptor, t);
  }

  /**
   * 通过反射的方式，读取特定属性的值
   *
   * @param descriptor 属性的字段描述对象
   * @param t 数据对象
   * @return 读取到的值或null
   */
  private Object readValue(PropertyDescriptor descriptor, Object t) {
    try {
      return descriptor.getReadMethod().invoke(t);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * 给指定对象{@code t}的特定字段{@code field}设定给定的值{@code value}
   *
   * @param field 对象属性名称
   * @param t 对象
   * @param value 需要设置的值
   */
  @SuppressWarnings("unchecked")
  public void setValue(String field, Object t, Object value) {
    if (field == null || t == null) {
      return;
    }
    String beanName = t.getClass().getName();
    Map<String, PropertyDescriptor> descriptorMap = descriptorCache.computeIfAbsent(beanName, name -> createPropertyDescriptorMap(t));

    synchronized (this) {
      int index = field.indexOf(".");

      if (index == -1) {
        writeValue(descriptorMap.get(field), t, value);
      }
    }
  }

  private PropertyDescriptor getDescriptorFromCache(String cacheKey, Object object) {
    Map<String, PropertyDescriptor> objectDescriptors = descriptorCache.computeIfAbsent(object.getClass().getName(), name -> createPropertyDescriptorMap(object));
    return objectDescriptors.get(cacheKey);
  }

  private Map<String, PropertyDescriptor> createPropertyDescriptorMap(Object object) {
    Map<String, PropertyDescriptor> descriptorMap = new HashMap<>();
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(), Object.class);
      PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

      for (PropertyDescriptor desc : descriptors) {
        descriptorMap.put(desc.getName(), desc);
      }
    } catch (IntrospectionException e) {
      throw new IllegalArgumentException(e);
    }
    return descriptorMap;
  }

  /**
   * 给给定的实例设置对象值
   * <p>
   * 使用场景多为在不知道field name的情况下，通过属性声明时的类型特征来判断是否属于该对象
   *
   * @param t 实例
   * @param value 值
   * @param <T> 对象实例泛型
   */
  @SuppressWarnings("all")
  public <T extends BaseData> void setValue(T t, Object value) {
    if (Objects.isNull(t) || Objects.isNull(value)) {
      return;
    }
    synchronized (this) {
      Class clazz = value.getClass();

      if (value instanceof Collection) {
        clazz = ((Collection) value).iterator().next().getClass();
      }
      writeValue(getDescriptor(t, clazz), t, value);
    }
  }

  /**
   * 通过实例对象和需要设置的值获取PropertyDescriptor对象，该方法有可能获取到null。
   *
   * @param t 实例对象
   * @param clazz 对象值的类型
   * @param <T> 对象泛型
   * @return PropertyDescriptor实例或者null。
   */
  private <T extends BaseData> PropertyDescriptor getDescriptor(T t, Class clazz) {
    Field[] fields = t.getClass().getDeclaredFields();
    String fieldName = null;

    for (Field field : fields) {
      if (field.getGenericType().getTypeName().contains(clazz.getName())) {
        fieldName = field.getName();
      }
    }
    if (Objects.isNull(fieldName)) {
      return null;
    }
    String beanName = t.getClass().getName();
    String cacheKey = beanName + HORIZONTAL_LINE_PLACEHOLDERS + fieldName;

    synchronized (this) {
      return getDescriptorFromCache(cacheKey, t);
    }
  }

  /**
   * 通过反射的机制，给特定对象的指定属性中设置给定的值
   *
   * @param descriptor 对象属性的描述对象
   * @param t 对象
   * @param value 需要设置的值
   */
  public void writeValue(PropertyDescriptor descriptor, Object t, Object value) {
    if (Objects.isNull(descriptor) || value == null) {
      return;
    }
    try {
      Method writeMethod = descriptor.getWriteMethod();

      if (writeMethod == null) {
        return;
      }
      Class<?>[] classes = writeMethod.getParameterTypes();
      writeMethod.invoke(t, castValue(classes[0], value));
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }


  /**
   * 基本数据类型之间的转换,若{@code clazz}是基本类型数据所对应的{@code class}， 则转换成相应的类型，否则返回{@code value}本身。
   *
   * @param clazz 数据类型
   * @param value 数据值
   * @return 转换成相应的数据类型或者value本身
   */
  private Object castValue(Class clazz, Object value) {
    if (value == null) {
      return null;
    }
    if (clazz == int.class) {
      return Integer.valueOf(StringUtils.isBlank(value.toString()) ? "0" : value.toString());
    }
    if (clazz == boolean.class) {
      return Boolean.valueOf(value.toString());
    }
    if (clazz == long.class) {
      return Long.valueOf(value.toString());
    }
    if (clazz == double.class) {
      return Double.valueOf(value.toString());
    }
    if (clazz == String.class) {
      return value.toString();
    }
    return clazz.isInstance(value) ? value : null;
  }


  public Map<String, PropertyDescriptor> getBeanPropertyDescriptors(Object object) {
    return descriptorCache.computeIfAbsent(object.getClass().getName(), name -> createPropertyDescriptorMap(object));
  }

}
