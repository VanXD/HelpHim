package com.vanxd.admin.dialect.processor;

import com.vanxd.data.dict.Dictionary;
import com.vanxd.data.util.DictionaryUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;

import java.util.Map;

/**
 * 将枚举类型中的整型转为中文含义
 * 例如：<span van:dict="StatusEnum" value="1" ></span>
 * 将会在标签里显示StatusEnum枚举类型中code=1的中文含义。
 *
 * @author wyd on 2016/9/23.
 */
public class DictTextProcessor extends AbstractTextChildModifierAttrProcessor {
    private final static String attribute = "dict";
    private final static String completeAttribute = "van:" + attribute;
    private final static String valueAttribute = "value";

    protected DictTextProcessor(String attributeName) {
        super(attributeName);
    }
    public static DictTextProcessor create() {
        return new DictTextProcessor(attribute);
    }

    @Override
    protected String getText(Arguments arguments, Element element, String attributeName) {
        Map<String, Attribute> attributeMap = element.getAttributeMap();
        Attribute value = attributeMap.get(valueAttribute);
        if(null == value) {
            return "";
        }
        int intValue = Integer.parseInt(value.getValue());
        Attribute className = attributeMap.get(completeAttribute);
        if(null == className) {
            return "";
        }
        Dictionary[] dictionaries = DictionaryUtils.getDictionaries(className.getValue());
        for (Dictionary dictionary : dictionaries) {
            if(dictionary.getCode() == intValue) {
                return dictionary.getText();
            }
        }
        return "";
    }

    @Override
    public int getPrecedence() {
        return 100;
    }
}
