package com.vanxd.admin.dialect;

import com.vanxd.admin.dialect.processor.SelectEleProcessor;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wyd on 2016/9/19.
 */
public class VanThymeleafDialect extends AbstractDialect {
    private static final String prefix = "van";

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(SelectEleProcessor.create());

        return Collections.unmodifiableSet(processors);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
}
