import java.io.Serializable;
import java.time.Instant;
public class MsgTrace implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -259873267823777852L;
	public final int senderIndex;
	public final int receiverIndex;
	public final Instant senderWallClock;
	public final Instant receiverWallClock;
	public final Timestamp senderCausalityClock;
	public final Timestamp receiverCausalityClock;
	
	public MsgTrace(int senderInd, int receiverInd, Instant senderPt, Instant receiverPt, Timestamp senderCausalityClk, Timestamp receiverCausalityClk) {
		this.senderIndex = senderInd;
		this.receiverIndex = receiverInd;
		this.senderWallClock = senderPt;
		this.receiverWallClock = receiverPt;
		this.senderCausalityClock = senderCausalityClk;
		this.receiverCausalityClock = new Timestamp(receiverCausalityClk);
	}
	public void Print()
	{
		System.out.println("senderInd:" + this.senderIndex + ",senderPt:" + this.senderWallClock);
		System.out.println("senderCausalityClk:<l:" + this.senderCausalityClock.getL() + ",c:" + this.senderCausalityClock.getC() + ">");
		System.out.println("receiverInd:" + this.receiverIndex + ", receiverPt:" + this.receiverWallClock);
		System.out.println("receiverCausalityClk:<l:" + this.receiverCausalityClock.getL() + ",c:" + this.receiverCausalityClock.getC() + ">");
	}
}
