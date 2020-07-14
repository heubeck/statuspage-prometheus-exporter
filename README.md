# Statuspage Prometheus Exporter

```
# HELP service_status_fetch_error Error counter for fetching statuses
# TYPE service_status_fetch_error counter
# HELP service_status Status of a service component, values 0 (operational) to 3 (major_outage)
# TYPE service_status gauge
service_status{service="GitHub",component="API Requests",} 0.0
service_status{service="GitHub",component="GitHub Actions",} 0.0
service_status{service="Quay.io",component="Atlassian Bitbucket Webhooks",} 0.0
service_status{service="GitHub",component="GitHub Pages",} 0.0
service_status{service="Quay.io",component="Database",} 0.0
service_status{service="GitHub",component="Visit www.githubstatus.com for more information",} 0.0
service_status{service="Quay.io",component="Build System",} 0.0
service_status{service="Quay.io",component="Atlassian Bitbucket SSH",} 0.0
service_status{service="GitHub",component="GitHub Packages",} 0.0
service_status{service="GitHub",component="Webhooks",} 0.0
service_status{service="Quay.io",component="Amazon Web Services",} 0.0
service_status{service="GitHub",component="Git Operations",} 0.0
service_status{service="Quay.io",component="GitHub",} 0.0
service_status{service="Quay.io",component="Registry",} 0.0
service_status{service="GitHub",component="Other",} 0.0
service_status{service="Quay.io",component="Security Scanning",} 0.0
service_status{service="Quay.io",component="Atlassian Bitbucket API",} 0.0
service_status{service="Quay.io",component="Plan Management",} 0.0
service_status{service="Quay.io",component="Payment Management",} 0.0
service_status{service="Quay.io",component="API",} 0.0
service_status{service="GitHub",component="Issues, Pull Requests, Projects",} 0.0
service_status{service="Quay.io",component="Frontend",} 0.0
```