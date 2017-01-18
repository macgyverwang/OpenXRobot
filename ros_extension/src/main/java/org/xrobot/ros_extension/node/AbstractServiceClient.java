package org.xrobot.ros_extension.node;

import org.ros.exception.RosRuntimeException;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceResponseListener;

/**
 * Created by macgyver on 2017/1/5.
 */

abstract public class AbstractServiceClient<S> {
    abstract public void onStart(final ConnectedNode connectedNode) throws RosRuntimeException;

    abstract public boolean command(String command, ServiceResponseListener<S> listener);

    abstract public void call(String data, ServiceResponseListener<S> listener);

    abstract public String[] getExpressions();
}
