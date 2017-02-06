package cc.advancedrobotics.robotclient.ros.implement;


import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.advancedrobotics.robotclient.ros.AbstractTopicPublisher;

/**
 * Created by macgyver on 2017/1/4.
 */

public class NavigationPublisher extends AbstractTopicPublisher {
    private static final String TAG = "NavigationPublisher";
    private Publisher<std_msgs.String> mPublisher;
    private String mExpression;

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        mExpression = ".*导航到(.*)";
        mPublisher = connectedNode.newPublisher(getTopicName(), std_msgs.String._TYPE);
    }

    @Override
    public String getTopicName() {
        return "/mobile/pager_command";
    }

    @Override
    public boolean command(String command) {
        for (String expression : getExpressions()) {
            Matcher matcher = Pattern.compile(expression).matcher(command);
            if (matcher.find()) {
                publish(command);
                return true;
            }
        }
        return false;
    }

    @Override
    public void publish(String data) {
        if (mPublisher != null) {
            final std_msgs.String message = mPublisher.newMessage();
            message.setData(data);
            mPublisher.publish(message);
        }
    }

    @Override
    public String[] getExpressions() {
        return new String[]{
                ".*导航到(.*)",
                ".*導航到(.*)"};
    }
}
