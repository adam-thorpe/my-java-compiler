package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable;

import com.adamthorpe.javacompiler.ClassFile.Attributes.Attributes_info;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>Contains data for the StackMapTable attribute.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.4</p>
 */
public class StackMapTable_attribute extends Attributes_info {

  protected byte[] number_of_entries; //u2
  protected byte[] entries;           //entries[number_of_entries]

  public StackMapTable_attribute(int attribute_name_index, StackMapEntries entries) {
    super(attribute_name_index);
    this.number_of_entries = ByteConvert.intToBytes(2, entries.size());
    this.entries = entries.getData();

    setAttributeLength(getThisLength());
  }

  /**
   * <p>Gets the length of the data in this class, used for the <code>super.attribute_length</code>.</p>
   * 
   * @return  Length of the data in this class
   */
  private int getThisLength() {
    return number_of_entries.length +
      entries.length;
  }

  @Override
  public int getLength() {
    return super.getLength() +
      getThisLength();
  }

  @Override
  public byte[] getData() {
    return ByteConvert.copyBytes(attribute_name_index,
      attribute_length,
      number_of_entries,
      entries
    );
  }

}
