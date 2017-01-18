package org.xrobot.ros_extension.node;

import org.ros.node.ConnectedNode;

/**
 * Created by macgyver on 2017/1/4.
 */
abstract public class AbstractTopicPublisher {
    abstract public void onStart(final ConnectedNode connectedNode);

    abstract public String getTopicName();

    abstract public boolean command(String command);

    abstract public void publish(String data);

    abstract public String[] getExpressions();
}
