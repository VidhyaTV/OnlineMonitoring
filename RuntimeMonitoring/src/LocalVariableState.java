import java.time.Instant;
public class LocalVariableState {

	private boolean x;
	private Instant localWallClock;
	private Timestamp localCausalityClock;
	
	public LocalVariableState(boolean xvalue, Instant pt, Timestamp causalityClock) {
		if(causalityClock.getType()!=TimestampType.HLC) {
			return; //current implementation expects only HLC timestamp
		}
		this.x=xvalue;
		this.localWallClock=pt;
		this.localCausalityClock=causalityClock;
	}
}

