package com.flh.demo.testPublish;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by fulinhua on 2017/8/13.
 */
public class Subscriber extends JedisPubSub {
    public Subscriber() {
    }

    public void onMessage(String channel, String message) {
        System.out.println(String.format("receive redis published message, channel %s, message %s", channel, message));
    }

    public void onPMessage(String s, String s1, String s2) {

    }

    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }

    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                channel, subscribedChannels));

    }

    public void onPUnsubscribe(String s, int i) {

    }

    public void onPSubscribe(String s, int i) {

    }
}