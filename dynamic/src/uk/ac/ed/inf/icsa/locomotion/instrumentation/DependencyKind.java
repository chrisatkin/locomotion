package uk.ac.ed.inf.icsa.locomotion.instrumentation;

public enum DependencyKind {
	ReadWrite,
	WriteRead,
	WriteWrite
	// we are unconcerned with read-read
}