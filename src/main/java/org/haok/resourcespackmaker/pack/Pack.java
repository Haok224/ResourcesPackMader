package org.haok.resourcespackmaker.pack;

@SuppressWarnings({"all"})
public class Pack{
    int pack_format;
    String description;

    public int getPack_format() {
        return pack_format;
    }

    public void setPack_format(int pack_format) {
        this.pack_format = pack_format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pack(){
    }
    public Pack(int pack_format,String description){
        this.description = description;
        this.pack_format = pack_format;
    }
}