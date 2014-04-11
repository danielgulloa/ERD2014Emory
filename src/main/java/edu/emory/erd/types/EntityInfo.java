package edu.emory.erd.types;

/**
 * Represents an entity from KB with its information (id, name, etc)
 */
final public class EntityInfo {
    private String id;
    private String name;

    public EntityInfo(String entityId, String entityName) {
        id = entityId;
        name = entityName;
    }

    /** returns entity id */
    public String getId() {
        return id;
    }

    /** returns entity name */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", id, name);
    }
}
