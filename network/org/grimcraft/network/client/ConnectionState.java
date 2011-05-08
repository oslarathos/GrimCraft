package org.grimcraft.network.client;

public enum ConnectionState {
	INITIAL,
	STARTING,
	READY,
	RUNNING,
	CLOSING,
	CLOSED,
	ERROR
}
