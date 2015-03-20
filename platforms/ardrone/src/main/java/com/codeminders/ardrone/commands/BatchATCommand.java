package com.codeminders.ardrone.commands;

import java.io.UnsupportedEncodingException;

import com.codeminders.ardrone.DroneCommand;

public class BatchATCommand extends DroneCommand {

	private ATCommand atCommand;

	private int batchSize;

	public BatchATCommand(ATCommand atCommand, int batchSize) {
		this.atCommand = atCommand;
		this.batchSize = batchSize;
	}

	public int getBatchSize() {
		return batchSize;
	}

	@Override
	public int getPriority() {
		return atCommand.getPriority();
	}

	public String getCommandStrings(int sequenceStart) {
		StringBuffer stringBuffer = new StringBuffer();
		int seq = sequenceStart;
		for (int i = 0; i < batchSize; i++) {
			stringBuffer.append(atCommand.getCommandString(seq++) + "\r");
		}
		return stringBuffer.toString();
	}

	public byte[] getPacket(int i) {
		try {
			return getCommandStrings(i).getBytes("ASCII");
		} catch (UnsupportedEncodingException e) {
			// never happens
			return null;
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(" [BATCH=");
		builder.append(getBatchSize());
		builder.append(", command=");
		builder.append(atCommand.toString());
		builder.append("]");
		return builder.toString();
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BatchATCommand))
			return false;
		BatchATCommand o = (BatchATCommand) obj;
		return o.getCommandStrings(0).equals(getCommandStrings(0));
	}

}
