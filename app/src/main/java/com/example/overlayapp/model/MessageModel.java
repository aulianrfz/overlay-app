package com.example.overlayapp.model;

import java.lang.ref.WeakReference;

public class MessageModel {

    public interface MessageListener {
        void onMessageReceived(String message);
    }

    private static MessageModel instance;
    private WeakReference<MessageListener> listenerRef;

    private MessageModel() {}

    public static synchronized MessageModel getInstance() {
        if (instance == null) {
            instance = new MessageModel();
        }
        return instance;
    }

    public void setListener(MessageListener listener) {
        if (listener != null) {
            listenerRef = new WeakReference<>(listener);
        } else {
            listenerRef = null;
        }
    }

    public void notifyMessage(String message) {
        if (listenerRef != null) {
            MessageListener listener = listenerRef.get();
            if (listener != null) {
                listener.onMessageReceived(message);
            }
        }
    }
}
