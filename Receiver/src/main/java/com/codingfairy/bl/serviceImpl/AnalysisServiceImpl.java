package com.codingfairy.bl.serviceImpl;

import com.codingfairy.bl.service.LocalAnalysisService;
import com.codingfairy.bl.config.NodeConfig;
import com.codingfairy.web.json.ProbeJson;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.List;

/**
 * @author darxyan
 * @version 2017-05-09 19:29:20
 */
public class AnalysisServiceImpl implements LocalAnalysisService {

    private Gson gson ;
    private Jedis jedis;
    public AnalysisServiceImpl() {
        gson = new Gson();
        jedis = new Jedis(NodeConfig.REDIS_SERVER, NodeConfig.REDIS_PORT);
        jedis.auth(NodeConfig.REDIS_AUTH);
    }

    public Object uploadFiles(String probJson) {
        try {
            jedis.publish(NodeConfig.CHAN_PROBJSON, probJson);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Object uploadFiles(ProbeJson probeJson) {
        try {
            jedis.publish(NodeConfig.CHAN_PROBJSON, gson.toJson(probeJson));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将列表数据提交到服务器
     * <em>提交完成后需要清空列表</em>
     *
     * @param probeJsons list of {@link ProbeJson}
     * @return ?
     */
    @Override
    public Object uploadFiles(List<ProbeJson> probeJsons) {
        return null;
    }

    @Override
    public Object getCalculatedAnalysis(Object dataType) {
        return jedis.get(dataType.toString());
    }

    @Override
    public Object getRealTimeAnalysis(Object dataType) {
        return jedis.get(dataType.toString());
    }


    public static void main(String[] args) {

        new Thread(()->
             {
                Jedis jedis = new Jedis(NodeConfig.REDIS_SERVER,NodeConfig.REDIS_PORT);
                jedis.auth(NodeConfig.REDIS_AUTH);
                jedis.subscribe(
                        new JedisPubSub() {
                            long startTime = 0;
                            @Override
                            public void onMessage(String channel, String message) {
                                if (startTime==0) {
                                    startTime = System.currentTimeMillis();
                                }
                                System.out.println(message);
                                System.out.println(System.currentTimeMillis()-startTime);
                            }
                        } ,
                        NodeConfig.CHAN_PROBJSON);
            }
        );

        AnalysisServiceImpl analysisService = new AnalysisServiceImpl();
        Gson gson = new Gson();
        String json = "{ \"id\": \"007db104\", \"data\": [ { \"mac\": \"60:0b:03:48:2b:b1\", \"rssi\": \"-76\", \"ch\": \"6\", \"range\": \"255\" }, { \"mac\": \"f0:b4:29:76:96:1f\", \"rssi\": \"-68\", \"ch\": \"6\", \"range\": \"129\" }, { \"mac\": \"00:23:89:30:a7:f0\", \"rssi\": \"-91\", \"ch\": \"6\", \"range\": \"918\" }, { \"mac\": \"80:f6:2e:21:9c:40\", \"rssi\": \"-56\", \"ch\": \"6\", \"range\": \"46\" }, { \"mac\": \"80:f6:2e:21:9c:41\", \"rssi\": \"-56\", \"ch\": \"6\", \"range\": \"46\" }, { \"mac\": \"80:f6:2e:21:9c:42\", \"rssi\": \"-56\", \"ch\": \"6\", \"range\": \"46\" }, { \"mac\": \"64:09:80:6b:40:2a\", \"rssi\": \"-55\", \"ch\": \"6\", \"range\": \"42\" }, { \"mac\": \"00:23:89:30:9a:50\", \"rssi\": \"-37\", \"ch\": \"6\", \"range\": \"10\" }, { \"mac\": \"00:23:89:30:9a:51\", \"rssi\": \"-37\", \"ch\": \"6\", \"range\": \"10\" }, { \"mac\": \"00:23:89:ba:50:32\", \"rssi\": \"-91\", \"ch\": \"6\", \"range\": \"918\" }, { \"mac\": \"60:0b:03:48:2b:b0\", \"rssi\": \"-75\", \"ch\": \"6\", \"range\": \"234\" }, { \"mac\": \"00:23:89:30:a7:f1\", \"rssi\": \"-93\", \"ch\": \"7\", \"range\": \"1089\" }, { \"mac\": \"60:0b:03:4b:54:d0\", \"rssi\": \"-87\", \"ch\": \"7\", \"range\": \"652\" }, { \"mac\": \"60:0b:03:4b:54:d1\", \"rssi\": \"-87\", \"ch\": \"7\", \"range\": \"652\" }, { \"mac\": \"00:23:89:30:a6:11\", \"rssi\": \"-60\", \"ch\": \"9\", \"range\": \"65\" }, { \"mac\": \"44:85:00:ab:3f:88\", \"rssi\": \"-30\", \"ch\": \"10\", \"ts\": \"Xiaomi_dzm\", \"tmc\": \"f0:b4:29:76:96:1f\", \"tc\": \"N\", \"range\": \"10\" }, { \"mac\": \"00:23:89:30:8d:90\", \"rssi\": \"-85\", \"ch\": \"10\", \"range\": \"550\" }, { \"mac\": \"00:23:89:30:8d:91\", \"rssi\": \"-85\", \"ch\": \"10\", \"range\": \"550\" }, { \"mac\": \"00:23:89:30:a6:10\", \"rssi\": \"-55\", \"ch\": \"10\", \"range\": \"42\" }, { \"mac\": \"60:0b:03:55:95:70\", \"rssi\": \"-72\", \"ch\": \"10\", \"range\": \"181\" }, { \"mac\": \"60:0b:03:55:95:71\", \"rssi\": \"-76\", \"ch\": \"11\", \"range\": \"255\" }, { \"mac\": \"80:f6:2e:21:9d:11\", \"rssi\": \"-80\", \"ch\": \"11\", \"range\": \"359\" }, { \"mac\": \"80:f6:2e:21:9d:12\", \"rssi\": \"-81\", \"ch\": \"11\", \"range\": \"391\" }, { \"mac\": \"3a:25:93:de:2c:4c\", \"rssi\": \"-97\", \"ch\": \"11\", \"range\": \"1531\" }, { \"mac\": \"80:f6:2e:27:14:11\", \"rssi\": \"-87\", \"ch\": \"11\", \"range\": \"652\" }, { \"mac\": \"88:25:93:de:2c:4c\", \"rssi\": \"-94\", \"ch\": \"11\", \"range\": \"1185\" }, { \"mac\": \"60:0b:03:48:4a:b0\", \"rssi\": \"-83\", \"ch\": \"1\", \"range\": \"464\" }, { \"mac\": \"60:0b:03:48:4a:b1\", \"rssi\": \"-80\", \"ch\": \"1\", \"range\": \"359\" }, { \"mac\": \"80:f6:2e:21:27:80\", \"rssi\": \"-74\", \"ch\": \"1\", \"range\": \"215\" }, { \"mac\": \"80:f6:2e:21:27:81\", \"rssi\": \"-74\", \"ch\": \"1\", \"range\": \"215\" }, { \"mac\": \"00:23:89:30:92:50\", \"rssi\": \"-73\", \"ch\": \"1\", \"range\": \"197\" }, { \"mac\": \"00:23:89:30:92:51\", \"rssi\": \"-76\", \"ch\": \"1\", \"range\": \"255\" }, { \"mac\": \"00:23:89:30:9d:11\", \"rssi\": \"-70\", \"ch\": \"1\", \"range\": \"153\" }, { \"mac\": \"bc:46:99:a9:82:7e\", \"rssi\": \"-86\", \"ch\": \"1\", \"range\": \"599\" }, { \"mac\": \"00:23:89:30:ac:50\", \"rssi\": \"-83\", \"ch\": \"1\", \"range\": \"464\" }, { \"mac\": \"00:23:89:30:ac:51\", \"rssi\": \"-82\", \"ch\": \"1\", \"range\": \"426\" }, { \"mac\": \"80:f6:2e:21:27:00\", \"rssi\": \"-67\", \"ch\": \"1\", \"range\": \"118\" }, { \"mac\": \"80:f6:2e:21:27:01\", \"rssi\": \"-68\", \"ch\": \"1\", \"range\": \"129\" }, { \"mac\": \"80:f6:2e:21:27:02\", \"rssi\": \"-68\", \"ch\": \"1\", \"range\": \"129\" }, { \"mac\": \"00:23:89:30:9d:10\", \"rssi\": \"-69\", \"ch\": \"1\", \"range\": \"140\" }, { \"mac\": \"60:0b:03:55:6b:a1\", \"rssi\": \"-91\", \"ch\": \"1\", \"range\": \"918\" }, { \"mac\": \"60:0b:03:55:a4:d0\", \"rssi\": \"-81\", \"ch\": \"5\", \"range\": \"391\" }, { \"mac\": \"60:0b:03:55:a4:d1\", \"rssi\": \"-81\", \"ch\": \"5\", \"range\": \"391\" }, { \"mac\": \"00:23:89:ba:50:30\", \"rssi\": \"-89\", \"ch\": \"6\", \"range\": \"774\" }, { \"mac\": \"00:23:89:ba:50:31\", \"rssi\": \"-89\", \"ch\": \"6\", \"range\": \"774\" }, { \"mac\": \"60:0b:03:48:66:70\", \"rssi\": \"-97\", \"ch\": \"6\", \"range\": \"1531\" }, { \"mac\": \"60:0b:03:48:66:71\", \"rssi\": \"-94\", \"ch\": \"6\", \"range\": \"1185\" }, { \"mac\": \"b8:ee:65:18:1f:c2\", \"rssi\": \"-62\", \"ch\": \"10\", \"ts\": \"Xiaomi_dzm\", \"tmc\": \"f0:b4:29:76:96:1f\", \"tc\": \"Y\", \"range\": \"77\" }, { \"mac\": \"80:f6:2e:21:9d:10\", \"rssi\": \"-81\", \"ch\": \"11\", \"range\": \"391\" }, { \"mac\": \"80:f6:2e:21:28:60\", \"rssi\": \"-62\", \"ch\": \"11\", \"range\": \"77\" }, { \"mac\": \"80:f6:2e:21:28:61\", \"rssi\": \"-62\", \"ch\": \"11\", \"range\": \"77\" }, { \"mac\": \"80:f6:2e:27:14:12\", \"rssi\": \"-91\", \"ch\": \"11\", \"range\": \"918\" }, { \"mac\": \"00:23:89:30:88:f0\", \"rssi\": \"-93\", \"ch\": \"11\", \"range\": \"1089\" }, { \"mac\": \"00:23:89:30:88:f1\", \"rssi\": \"-91\", \"ch\": \"11\", \"range\": \"918\" }, { \"mac\": \"80:f6:2e:27:14:10\", \"rssi\": \"-89\", \"ch\": \"11\", \"range\": \"774\" }, { \"mac\": \"06:0a:01:0b:c5:ea\", \"rssi\": \"-89\", \"ch\": \"1\", \"range\": \"774\" }, { \"mac\": \"80:f6:2e:21:27:82\", \"rssi\": \"-73\", \"ch\": \"1\", \"range\": \"197\" }, { \"mac\": \"60:0b:03:55:6b:a0\", \"rssi\": \"-92\", \"ch\": \"1\", \"range\": \"1000\" }, { \"mac\": \"c4:ca:d9:87:17:e0\", \"rssi\": \"-95\", \"ch\": \"1\", \"range\": \"1291\" }, { \"mac\": \"c4:ca:d9:87:17:e2\", \"rssi\": \"-94\", \"ch\": \"1\", \"range\": \"1185\" }, { \"mac\": \"00:23:89:30:89:91\", \"rssi\": \"-96\", \"ch\": \"6\", \"range\": \"1406\" }, { \"mac\": \"60:0b:03:55:94:20\", \"rssi\": \"-91\", \"ch\": \"6\", \"range\": \"918\" }, { \"mac\": \"00:23:89:30:90:31\", \"rssi\": \"-94\", \"ch\": \"6\", \"range\": \"1185\" }, { \"mac\": \"80:f6:2e:21:28:62\", \"rssi\": \"-63\", \"ch\": \"11\", \"range\": \"84\" }, { \"mac\": \"84:1b:5e:3f:48:22\", \"rssi\": \"-94\", \"ch\": \"6\", \"range\": \"1185\" }, { \"mac\": \"60:0b:03:55:94:21\", \"rssi\": \"-95\", \"ch\": \"6\", \"range\": \"1291\" }, { \"mac\": \"00:23:89:30:89:90\", \"rssi\": \"-96\", \"ch\": \"6\", \"range\": \"1406\" }, { \"mac\": \"20:f4:1b:7d:b1:04\", \"rssi\": \"-20\", \"ch\": \"9\", \"ts\": \"Xiaomi_dzm\", \"tmc\": \"f0:b4:29:76:96:1f\", \"tc\": \"N\", \"range\": \"10\" }, { \"mac\": \"6a:25:93:de:2c:4c\", \"rssi\": \"-92\", \"ch\": \"10\", \"range\": \"1000\" } ], \"mmac\": \"20:f4:1b:7d:b1:04\", \"rate\": \"3\", \"time\": \"Wed Apr 19 17:56:02 2017\", \"lat\": \"\", \"lon\": \"\" }\n";
        ProbeJson probeJson = gson.fromJson(json, ProbeJson.class);
        long start = (System.currentTimeMillis());
        for (int i=0; i<20; i++) {
            new Thread(()->{
                AnalysisServiceImpl analysis = new AnalysisServiceImpl();
                for (int j=0; j<50; j++) {
                    analysis.uploadFiles(probeJson);
                }
                System.out.println(System.currentTimeMillis()-start);
            }).start();
        }
    }


}
