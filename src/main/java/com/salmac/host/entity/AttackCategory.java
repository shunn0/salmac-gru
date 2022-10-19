package com.salmac.host.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum AttackCategory {
    Reconnaissance("Reconnaissance"),
    ResourceDevelopment("Resource Development"),
    InitialAccess("Initial Access"),
    Execution("Execution"),
    Persistence("Persistence"),
    PrivilegeEscalation("Privilege Escalation"),
    DefenseEvasion("Defense Evasion"),
    CredentialAccess("Credential Access"),
    Discovery("Discovery"),
    LateralMovement("Lateral Movement"),
    Collection("Collection"),
    CommandAndControl("Command and Control"),
    Exfiltration("Exfiltration"),
    Impact("Impact");

    public final String category;

    private AttackCategory(String category) {
        this.category = category;
    }

    public static List<Category> getCategoryMap(){
        List<Category> categories = new ArrayList<>();
        for (AttackCategory c : AttackCategory.values()) {
            categories.add(new Category(c.name(), c.category));
        }
        return categories;
    }

    public static class Category{
        private String key;
        private String value;
        public Category(String key, String value) {
            this.key = key;
            this.value = value;
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
    }


}
