package dto;

public class DullPacket {

    private String data;

    private DullPacket() {

    }

    public String getData() {
        return data;
    }

    public Builder getBuilder() {
        return new DullPacket().new Builder();
    }

    public class Builder {
        public Builder setDate(String data) {
            DullPacket.this.data = data;
            return this;
        }

        public DullPacket build() {
            return DullPacket.this;
        }
    }
}
