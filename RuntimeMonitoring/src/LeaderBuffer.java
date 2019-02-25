import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class LeaderBuffer {
	public LeaderBuffer(int numberOfMembers) {
		this.leaderBuffer = new ArrayList<>();
		for(int i=0;i<numberOfMembers;i++) {
			leaderBuffer.add(new ConcurrentSkipListMap<String,LocalTraceCollector>());
		}
	}
	public void clear() {
		int size=leaderBuffer.size();
		leaderBuffer = new ArrayList<>();
		for(int i=0;i<size;i++) {
			leaderBuffer.add(new ConcurrentSkipListMap<String,LocalTraceCollector>());
		}
	}
	public void updateBuffer(int processId,String newKey,LocalTraceCollector Traces) {
		ConcurrentSkipListMap<String,LocalTraceCollector> tempSubBuffer = this.leaderBuffer.get(processId);
		tempSubBuffer.put(newKey, Traces);
		System.out.println("tempSubBuffer.size():"+tempSubBuffer.size());
		this.leaderBuffer.set(processId,tempSubBuffer);
	}
	public boolean isBufferFilledForProcess(int processId) {
		if(this.leaderBuffer.get(processId).isEmpty()) {
			return false;
		}
		return true;
	}
	public int sizeOfBufferFilledForProcess(int processId) {
		return this.leaderBuffer.get(processId).size();
	}
	public void removeFromBufferTill(int processId, String key) {
		ConcurrentSkipListMap<String,LocalTraceCollector> tempBuff= getBufferofProc(processId);
		tempBuff.headMap(key, true).clear();
		this.leaderBuffer.set(processId,tempBuff);
	}
	/*
	private String getLastKeyAtProcess(int processId) {
		return this.leaderBuffer.get(processId).lastKey();
	}	
	private LocalTraceCollector getValueForKeyAtProcess(int processId, String key) {
		return this.leaderBuffer.get(processId).get(key);
	}	
	public Timestamp getRightEndofLastWindowFor(int processId) {
		//get last key in the unprocessed buffer corresponding to the process
		String lKey=getLastKeyAtProcess(processId);
		//get the value(window-report) corresponding to the last key and then get the ending causality clock value
		return getValueForKeyAtProcess(processId,lKey).getEndCausalClkInEpsilonWindow();
	}
	//add to buffer
	private void addToBufferFromNavMap(int processId,ConcurrentNavigableMap<String,LocalTraceCollector> windowsToAdd) {
		ConcurrentSkipListMap<String,LocalTraceCollector> tempSubBuffer = this.leaderBuffer.get(processId);
		tempSubBuffer.putAll(windowsToAdd);
		System.out.println("tempSubBuffer.size():"+tempSubBuffer.size());
		this.leaderBuffer.set(processId,tempSubBuffer);
	}
	//extract till key
	private ConcurrentNavigableMap<String,LocalTraceCollector> getSubBuffFromHeadTill(int processId, String key){
		ConcurrentSkipListMap<String,LocalTraceCollector> tempSubBuffer = this.leaderBuffer.get(processId);
		return tempSubBuffer.headMap(key, true);
	}
	public void moveIntoFromBuffTill(LeaderBuffer otherBuffer, int processId, String key) {
		//extract till key
		ConcurrentNavigableMap<String,LocalTraceCollector> subBuff = otherBuffer.getSubBuffFromHeadTill(processId,key);
		//add to buffer
		addToBufferFromNavMap(processId,subBuff);
	}
	*/
	public ConcurrentSkipListMap<String,LocalTraceCollector> getBufferofProc(int processId){
		return this.leaderBuffer.get(processId);
	}
	public void printLeaderBuffer(String outputFilename) {
		try {		
			FileWriter file = new FileWriter(outputFilename ,false);
			int proc=1;
			while(proc<leaderBuffer.size()-1) {				
				if(isBufferFilledForProcess(proc)) {
					file.append("\nAt P"+proc+".Looping through the buffer of size "+sizeOfBufferFilledForProcess(proc));
					for (String s : getBufferofProc(proc).keySet()) {
						file.append("\nKey"+s+",<L:"+getBufferofProc(proc).get(s).getStartCausalClkInEpsilonWindow().getL()+",C;"+getBufferofProc(proc).get(s).getStartCausalClkInEpsilonWindow().getC()+">");
						file.append("to <L:"+getBufferofProc(proc).get(s).getEndCausalClkInEpsilonWindow().getL()+",C;"+getBufferofProc(proc).get(s).getEndCausalClkInEpsilonWindow().getC()+">");
					}
				}
				proc++;
			}
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private ArrayList<ConcurrentSkipListMap<String,LocalTraceCollector>> leaderBuffer;
}
