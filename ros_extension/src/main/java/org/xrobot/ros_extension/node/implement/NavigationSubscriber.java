package cc.advancedrobotics.robotclient.ros.implement;

import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import cc.advancedrobotics.robotclient.ros.AbstractTopicSubscriber;

/**
 * Created by macgyver on 2017/1/4.
 */

public class NavigationSubscriber extends AbstractTopicSubscriber {
    private static final String TAG = "NavigationSubscriber";
    private Subscriber<std_msgs.String> mSubscriber;
    @Override
    public void onStart(ConnectedNode connectedNode) {
        mSubscriber = connectedNode.newSubscriber(getTopicName(), std_msgs.String._TYPE);
    }

    @Override
    public String getTopicName() {
        return "/mobile/pager_result";
    }

    @Override
    public void addMessageListener(MessageListener listener) {
        mSubscriber.addMessageListener(listener);
    }
}
