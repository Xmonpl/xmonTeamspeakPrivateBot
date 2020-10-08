package cf.xmon.fastchannel;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import java.util.*;

public class FastChannelEvent extends TS3EventAdapter {
    private final List<String> uuids = Arrays.asList("S+S1H+IljnueogQZxSNdRROfiMk=", "Vh5IWCrmsu/5VTwC+bbitL+SvHQ=");
    private final Random rand = new Random();
    private final String[] arr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    @Override
    public void onClientMoved(final ClientMovedEvent e) {
        Client cll = App.query.getApi().getClientInfo(e.getClientId());
        if (e.getTargetChannelId() == 261) {
            String pin = arr[rand.nextInt(arr.length)];
            for (int i = 0; i < 3; i++) {
                int index = rand.nextInt(arr.length);
                pin += arr[index];
            }
            final Map<ChannelProperty, String> properties = new HashMap<>();
            properties.put(ChannelProperty.CHANNEL_FLAG_TEMPORARY, "1");
            properties.put(ChannelProperty.CHANNEL_CODEC_QUALITY, "10");
            properties.put(ChannelProperty.CHANNEL_PASSWORD, pin);
            properties.put(ChannelProperty.CHANNEL_DELETE_DELAY, "60");
            properties.put(ChannelProperty.CHANNEL_ORDER, String.valueOf(261));
            properties.put(ChannelProperty.CHANNEL_NAME, "Szybki kanał - " + cll.getNickname());
            App.query.getApi().createChannel("Szybki kanał - " + cll.getNickname(), properties);
            final int chid = App.query.getApi().getChannelByNameExact("Szybki kanał - " + cll.getNickname(), false).getId();
            App.query.getApi().moveClient(cll.getId(), chid);
            App.query.getApi().setClientChannelGroup(13, chid, cll.getDatabaseId());
            App.query.getApi().pokeClient(cll.getId(), "[color=lightgreen]Kanał został stworzony[/color][color=lightblue], hasło na ten kanał: [B]" + pin);
            App.query.getApi().moveClient(App.query.getApi().whoAmI().getId(), 94);
        }
        if (e.getTargetChannelId() == 275) {
            if (!uuids.contains(cll.getUniqueIdentifier())){
                App.query.getApi().kickClientFromChannel("oj nie byczq -1", cll);
                App.query.getApi().pokeClient(cll.getId(), "[b][color=red]oj nie byczq -1");
            }
        }
    }
}
