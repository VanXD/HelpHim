package com.vanxd.admin.dialect.processor;

import com.vanxd.data.dict.Dictionary;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wyd on 2016/9/19.
 */
public class SelectEleProcessor extends AbstractMarkupSubstitutionElementProcessor {
    private static final String dictAttrName = "dict";

    protected SelectEleProcessor(String elementName) {
        super(elementName);
    }

    public static SelectEleProcessor create() {
        return new SelectEleProcessor("select");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        final Element container = new Element("select");
        Map<String, Attribute> attributeMap = element.getAttributeMap();
        for(Map.Entry<String, Attribute> entry : attributeMap.entrySet()) {
            container.setAttribute(entry.getKey(), entry.getValue().getValue());
        }
        addOptions(container, attributeMap);
        final List<Node> nodes = new ArrayList<Node>();
        nodes.add(container);
        return nodes;
    }

    /**
     * 如果属性中有{dictAttrName}则查找相应的class，获得所有枚举对象，再生成option添加到select中
     * @param container
     * @param attributeMap
     */
    private void addOptions(Element container, Map<String, Attribute> attributeMap) {
        if (attributeMap.containsKey(dictAttrName)) {
            String dict = attributeMap.get(dictAttrName).getValue();
            Class<?> clazz = null;
            try {
                clazz = Class.forName("com.vanxd.data.dict." + dict);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Dictionary[] dictionaries = getDictionaries(clazz);
            buildOptions(container, dictionaries);
        }
    }

    /**
     * 获得所有枚举类型
     * @param clazz
     * @return
     */
    private Dictionary[] getDictionaries(Class<?> clazz) {
        Dictionary[] dictionaries = null;
        if(null != clazz) {
            dictionaries = (Dictionary[]) clazz.getEnumConstants();
        }
        return dictionaries;
    }

    /**
     * 将枚举类型构造为option，并添加到select中
     * @param container
     * @param dictionaries
     */
    private void buildOptions(Element container, Dictionary[] dictionaries) {
        Element option = null;
        Text text = null;
        for(Dictionary dictionary : dictionaries) {
            option = new Element("option");
            option.setAttribute("value", dictionary.getCode().toString());
            text = new Text(dictionary.getText());
            option.addChild(text);
            container.addChild(option);
        }
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }
}
