package org.xrobot.ros_extension.node;

import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;


/**
 * Created by macgyver on 2017/1/4.
 */
abstract public class AbstractTopicSubscriber<T> {
    abstract public void onStart(final ConnectedNode connectedNode);

    abstract public String getTopicName();

    abstract public void addMessageListener(MessageListener<T> var1);
}
