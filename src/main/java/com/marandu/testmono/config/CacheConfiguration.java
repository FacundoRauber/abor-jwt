package com.marandu.testmono.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.marandu.testmono.domain.VinculoFamiliar.class.getName());
            createCache(cm, com.marandu.testmono.domain.TipoPlanAsistencia.class.getName());
            createCache(cm, com.marandu.testmono.domain.Nacionalidad.class.getName());
            createCache(cm, com.marandu.testmono.domain.NivelEducativo.class.getName());
            createCache(cm, com.marandu.testmono.domain.TipoOcupacion.class.getName());
            createCache(cm, com.marandu.testmono.domain.Integrante.class.getName());
            createCache(cm, com.marandu.testmono.domain.Integrante.class.getName() + ".relevamientos");
            createCache(cm, com.marandu.testmono.domain.Comunidad.class.getName());
            createCache(cm, com.marandu.testmono.domain.Comunidad.class.getName() + ".integrantes");
            createCache(cm, com.marandu.testmono.domain.OrigenEnergia.class.getName());
            createCache(cm, com.marandu.testmono.domain.OrigenEnergia.class.getName() + ".relevamientos");
            createCache(cm, com.marandu.testmono.domain.OrigenAgua.class.getName());
            createCache(cm, com.marandu.testmono.domain.OrigenAgua.class.getName() + ".relevamientos");
            createCache(cm, com.marandu.testmono.domain.TipoVivienda.class.getName());
            createCache(cm, com.marandu.testmono.domain.TipoServicio.class.getName());
            createCache(cm, com.marandu.testmono.domain.TipoServicio.class.getName() + ".relevamientos");
            createCache(cm, com.marandu.testmono.domain.TipoTratamientoBasura.class.getName());
            createCache(cm, com.marandu.testmono.domain.Relevamiento.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
