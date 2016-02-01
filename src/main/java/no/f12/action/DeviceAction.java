package no.f12.action;

import no.f12.domain.AbstractAction;
import no.f12.telldus.live.domain.Device;

public class DeviceAction extends AbstractAction {

	private Device device;
	private String action;

	public DeviceAction(Device device, String string) {
		this.device = device;
		this.action = string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceAction other = (DeviceAction) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		return true;
	}
	
	

}
