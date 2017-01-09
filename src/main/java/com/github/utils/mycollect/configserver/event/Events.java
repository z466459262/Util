package com.github.utils.mycollect.configserver.event;

/**
 * 事件本身其实没太大用处，是一个消息的载体的桥梁，目的是把携带的信息传递给listener,
 * 所以Event的使命就是：1.消息类型是什么，这里直接用class代替。2.消息本身是什么
 */
public class Events {
	public static class ServerlistChangeEvent extends EventDispatcher.Event {
	}

	public static class AddSubscriberEvent extends EventDispatcher.Event {
		// public final Subscriber subscriber;

		public AddSubscriberEvent(/** Subscriber sub **/
		) {
			// this.subscriber = sub;
		}
	}

	public static class RunModeChangeEvent extends EventDispatcher.Event {
	}
}
