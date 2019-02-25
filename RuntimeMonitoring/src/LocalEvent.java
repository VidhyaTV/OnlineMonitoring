import java.io.Serializable;
import java.time.Instant;
//import org.jgroups.Message;

public class LocalEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1041937224082446109L;
	public LocalEvent(EventType eventType, Timestamp localTimestamp, Instant localWallClock, boolean xCurrentValue) {
		this.eventType = eventType;
		this.localCausalityClock = new Timestamp(localTimestamp);
		this.localWallClock = localWallClock;
		this.x = xCurrentValue;
	}
	public final EventType eventType; 
	public final Timestamp localCausalityClock;
	public final Instant localWallClock;  
	public final boolean x;
}
