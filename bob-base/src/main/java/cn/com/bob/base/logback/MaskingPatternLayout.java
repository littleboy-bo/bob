package cn.com.bob.base.logback;

import ch.qos.logback.classic.PatternLayout;
import cn.com.bob.base.util.StringUtils;
import cn.com.bob.base.util.StringUtils;


import java.util.Optional;

import java.util.regex.Pattern;


public class MaskingPatternLayout extends PatternLayout {
    private String patternsProperty;
    private Optional<Pattern> pattern;

    public MaskingPatternLayout() {
    }

    public String getPatternsProperty() {
        return this.patternsProperty;
    }

    public void setPatternsProperty(String patternsProperty) {
        this.patternsProperty = patternsProperty;
        if (StringUtils.isEmpty(this.patternsProperty)) {
            this.pattern = Optional.of(Pattern.compile(patternsProperty, 8));
        } else {
            this.pattern = Optional.empty();
        }

    }

}

