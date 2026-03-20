# .jenkins modular

Este layout separa stages por archivo para mantener el pipeline legible y reusable.

- build/prepare.groovy
- build/build.groovy
- build/test.groovy
- package/package.groovy
- deploy/cleanup.groovy
- deploy/deploy.groovy
