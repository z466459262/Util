package com.github.utils.mycollect.configserver.event;

import com.github.utils.mycollect.configserver.log.ConfigClientLogger;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 事件分发器
 * 
 * 
 */
public class EventDispatcher {
	protected static final Logger                                                           log         = ConfigClientLogger.logger();
	// CopyOnWriteArrayList是ArrayList
	// 的一个线程安全的变体，其中所有可变操作（add、set等等）都是通过对底层数组进行一次新的复制来实现的
	static final           Map<Class<? extends Event>, CopyOnWriteArrayList<EventListener>> listenerMap = new HashMap<Class<? extends Event>, CopyOnWriteArrayList<EventListener>>();

	public static void addEventListener(EventListener listener) {
		for (Class type : listener.interest()) {
			// 同步写入
			getListenerList(type).addIfAbsent(listener);
		}

	}

	public static void fireEvent(Event event) {
		if (null == event) {
			return;
		}

		for (Event implyEvent : event.implyEvents()) {
			try {
				if (event != implyEvent)
					fireEvent(implyEvent);
			} catch (Exception e) {
				log.error(e.toString(), e);
			}
		}

		for (EventListener listener : getListenerList(event.getClass()))
			try {
				listener.onEvent(event);
			} catch (Exception e) {
				log.error(e.toString(), e);
			}
	}

	static synchronized CopyOnWriteArrayList<EventListener> getListenerList(
			Class<? extends Event> eventType) {
		CopyOnWriteArrayList listeners = (CopyOnWriteArrayList) listenerMap
				.get(eventType);
		if (null == listeners) {
			listeners = new CopyOnWriteArrayList();
			listenerMap.put(eventType, listeners);
		}
		return listeners;
	}

	public static abstract class EventListener {
		public EventListener() {
			EventDispatcher.addEventListener(this);
		}

		public abstract List<Class<? extends Event>> interest();

		public abstract void onEvent(EventDispatcher.Event paramEvent);
	}

	public static abstract class Event {
		protected List<Event> implyEvents() {
			return Collections.EMPTY_LIST;
		}
	}
}
