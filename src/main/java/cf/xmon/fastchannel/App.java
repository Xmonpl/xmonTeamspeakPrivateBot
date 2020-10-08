package cf.xmon.fastchannel;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.reconnect.ConnectionHandler;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;

public class App {
    public static TS3Query query;
    public static TS3Api api;
    public static void main(String[] args) {
        final TS3Config config = new TS3Config();
        config.setHost("127.0.0.1");
        config.setQueryPort(10011);
        config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        config.setEnableCommunicationsLogging(false);
        config.setReconnectStrategy(ReconnectStrategy.exponentialBackoff());
        config.setConnectionHandler(new ConnectionHandler() {
            @Override
            public void onConnect(TS3Api ts3Api) {
                connect(ts3Api);
            }

            @Override
            public void onDisconnect(TS3Query ts3Query) {

            }
        });
        query = new TS3Query(config);
        query.connect();
        query.getApi().addTS3Listeners(new FastChannelEvent());
    }

    private static void connect(TS3Api ts3Api) {
        ts3Api.login("serveradmin", "8Borzjcn");
        ts3Api.selectVirtualServerByPort(9987,"Xmon @ System");
        ts3Api.registerAllEvents();
    }

}
