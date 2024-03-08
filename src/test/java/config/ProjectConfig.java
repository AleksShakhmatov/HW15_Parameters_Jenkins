package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/${environment}.properties")
public interface ProjectConfig extends Config {
    @Key("first_name")
    default String firstName() {
        return null;
    }

    @Key("last_name")
    default String lastName() {
        return null;
    }
}