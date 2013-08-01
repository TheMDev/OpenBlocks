package openblocks.sync;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

public class SyncableFloat extends SyncableObject implements ISyncableObject {

	public static final float EPSILON = 0.0001f;

	public SyncableFloat(float value) {
		super(value);
	}

	@Override
	public boolean equals(Object otherValue) {
		return Math.abs((Float)otherValue - (Float)value) < EPSILON;
	}

	@Override
	public void readFromStream(DataInputStream stream) throws IOException {
		value = stream.readFloat();
	}

	@Override
	public void writeToStream(DataOutputStream stream) throws IOException {
		stream.writeFloat((Float)value);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag, String name) {
		if (tiles.size() > 1) {
			tag.setFloat(name, (Float)value / tiles.size());
		}else {
			tag.setFloat(name, (Float)value);
		}
		super.writeToNBT(tag, name);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag, String name) {
		value = tag.getFloat(name);
		super.readFromNBT(tag, name);
	}

	@Override
	public void merge(ISyncableObject o) {
		if (o instanceof SyncableFloat) {
			modify((Float)((SyncableFloat)o).getValue());
			((SyncableFloat)o).setValue(0);
		}
	}

	@Override
	public void clear() {
		value = 0;
	}

	public void modify(float by) {
		this.setValue((Float)this.getValue() + by);
	}
}
