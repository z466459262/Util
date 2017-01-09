package com.github.utils.mycollect.configserver.event;

import java.util.ArrayList;
import java.util.List;

public class FailoverModeListener extends EventDispatcher.EventListener {

	public List<Class<? extends EventDispatcher.Event>> interest() {
		List<Class<? extends EventDispatcher.Event>> eventTypes = new ArrayList<Class<? extends EventDispatcher.Event>>();
		eventTypes.add(Events.RunModeChangeEvent.class);
		eventTypes.add(Events.AddSubscriberEvent.class);
		return eventTypes;
	}

	public void onEvent(EventDispatcher.Event event) {
		if (event instanceof Events.RunModeChangeEvent) {
			if (RunMode.isFailoverMode())
				onChangeToFailover();
			else {
				onChangeToNormal();
			}
		} else if (event instanceof Events.AddSubscriberEvent)
			onAddSubscriber((Events.AddSubscriberEvent) event);
	}

	void onChangeToFailover() {
		if (RunMode.isFailoverMode()) {
			// do something
		}
	}

	void onAddSubscriber(Events.AddSubscriberEvent event) {
		if (RunMode.isFailoverMode()) {
			// do something
		}
	}

	void onChangeToNormal() {
		if (RunMode.isNormalMode()) {
			// do something
		}
	}
}
