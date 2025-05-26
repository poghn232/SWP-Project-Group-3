public class SystemConfig {
    private int configId;
    private String key;
    private String value;
    private int updatedBy;

    public SystemConfig(int configId, String key, String value, int updatedBy) {
        this.configId = configId;
        this.key = key;
        this.value = value;
        this.updatedBy = updatedBy;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }
}
