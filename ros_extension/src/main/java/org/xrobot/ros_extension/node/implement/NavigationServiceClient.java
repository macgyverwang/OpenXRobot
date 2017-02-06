package cc.advancedrobotics.robotclient.ros.implement;

import android.util.Log;

import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.advancedrobotics.robotclient.ros.AbstractServiceClient;
import william_msgs.PagerString;
import william_msgs.PagerStringRequest;
import william_msgs.PagerStringResponse;

/**
 * Created by macgyver on 2017/1/5.
 */

public class NavigationServiceClient extends AbstractServiceClient {
    private static final String TAG = "NavigationServiceClient";

    private ServiceClient<PagerStringRequest, PagerStringResponse> mPagerServiceClient;

    @Override
    public void onStart(ConnectedNode connectedNode) throws RosRuntimeException {
        try {
            mPagerServiceClient = connectedNode.newServiceClient("Pager_Service", PagerString._TYPE);
        } catch (ServiceNotFoundException e) {
            Log.e(TAG, "Pager_Service ServiceNotFoundException");
            throw new RosRuntimeException(e);
        }
    }

    @Override
    public boolean command(String command, ServiceResponseListener listener) {
        Matcher matcher;

        for (String expression : getExpressions()) {
            matcher = Pattern.compile(expression).matcher(command);
            if (matcher.find()) {
                call(command, listener);
                return true;
            }
        }
        return false;
    }

    @Override
    public void call(String data, ServiceResponseListener listener) {
        if (mPagerServiceClient == null) {
            return;
        }
        final PagerStringRequest request = mPagerServiceClient.newMessage();
        request.setCmdRequest(data);
        Log.d(TAG, "requestPagerService:" + data);
        mPagerServiceClient.call(request, listener);
    }

    @Override
    public String[] getExpressions() {
        return new String[]{
                ".*导航到(.*)",
                ".*導航到(.*)"};
    }
}
