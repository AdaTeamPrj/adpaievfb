package com.adateam.adpaievfb.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.adateam.adpaievfb.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.adateam.adpaievfb.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.adateam.adpaievfb.domain.User.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Authority.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.User.class.getName() + ".authorities");
            createCache(cm, com.adateam.adpaievfb.domain.Location.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Location.class.getName() + ".employees");
            createCache(cm, com.adateam.adpaievfb.domain.Location.class.getName() + ".employeurs");
            createCache(cm, com.adateam.adpaievfb.domain.HeureSup.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Employee.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Employee.class.getName() + ".jobs");
            createCache(cm, com.adateam.adpaievfb.domain.Employee.class.getName() + ".locations");
            createCache(cm, com.adateam.adpaievfb.domain.Employeur.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Employeur.class.getName() + ".conventionCollectives");
            createCache(cm, com.adateam.adpaievfb.domain.Employeur.class.getName() + ".locations");
            createCache(cm, com.adateam.adpaievfb.domain.Job.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Job.class.getName() + ".employees");
            createCache(cm, com.adateam.adpaievfb.domain.TauxDImposition.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.MentionsObligatoires.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.MentionsObligatoires.class.getName() + ".ficheDePaies");
            createCache(cm, com.adateam.adpaievfb.domain.Conge.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Bonus.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Contrat.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.TypeContrat.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.TypeContrat.class.getName() + ".contrats");
            createCache(cm, com.adateam.adpaievfb.domain.TypeContrat.class.getName() + ".cotisations");
            createCache(cm, com.adateam.adpaievfb.domain.FicheDePaie.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.FicheDePaie.class.getName() + ".cotisations");
            createCache(cm, com.adateam.adpaievfb.domain.FicheDePaie.class.getName() + ".mentionsObligatoires");
            createCache(cm, com.adateam.adpaievfb.domain.ConventionCollective.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.ConventionCollective.class.getName() + ".employeurs");
            createCache(cm, com.adateam.adpaievfb.domain.Cotisation.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Cotisation.class.getName() + ".ficheDePaies");
            createCache(cm, com.adateam.adpaievfb.domain.Cotisation.class.getName() + ".typeContrats");
            createCache(cm, com.adateam.adpaievfb.domain.BaseTauxCotisation.class.getName());
            createCache(cm, com.adateam.adpaievfb.domain.Indicateurs.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
