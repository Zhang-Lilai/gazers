package org.tgo.gazerbase.tech.console;

import org.tgo.gazerbase.common.consumer.MessageConsumer;

public interface Computer extends MessageConsumer {
    String[] getCliContent();
}
