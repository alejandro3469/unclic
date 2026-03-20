/**
 * Unclic Tracker v1.0
 * Adaptador de rastreo de leads y estadísticas para Partners de FastFlow.
 */

const UnclicTracker = (function() {
    let config = {
        partnerId: 'default',
        domain: 'unclic.consulting',
        endpoint: 'https://track.unclic.consulting/v1/event'
    };

    return {
        init: function(userConfig) {
            config = { ...config, ...userConfig };
            console.log(`🚀 UnclicTracker initialized for partner: ${config.partnerId}`);
            this.trackEvent('page_view');
        },

        trackEvent: function(eventName, data = {}) {
            const payload = {
                event: eventName,
                partner_id: config.partnerId,
                timestamp: new Date().toISOString(),
                url: window.location.href,
                referrer: document.referrer,
                ...data
            };

            console.log(`📊 Tracking event: ${eventName}`, payload);

            // En un entorno real, enviaríamos esto vía fetch
            /*
            fetch(config.endpoint, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            }).catch(err => console.error('Tracking error:', err));
            */
        },

        trackLead: function(formData) {
            this.trackEvent('lead_generated', {
                email: formData.email,
                sector: formData.sector,
                interest: 'fastflow_implementation'
            });
            alert('Gracias por tu interés. Un consultor de Web Cuántica se pondrá en contacto contigo.');
        }
    };
})();

// Exportar para uso global
window.UnclicTracker = UnclicTracker;
