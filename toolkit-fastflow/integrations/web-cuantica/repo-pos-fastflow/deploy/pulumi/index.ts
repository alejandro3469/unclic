/**
 * index.ts — programa Pulumi (FastFlow POS, laboratorio)
 * =========================================================
 *
 * PROPÓSITO
 *   Crear un bucket S3 (recurso AWS) como ejemplo de IaC en el mismo monorepo
 *   donde vive el Jenkinsfile del POS. La app Java NO se despliega desde aquí.
 *
 * RELACIÓN CON OTROS ARCHIVOS
 *   - ../terraform/main.tf              → POS en Kubernetes (imagen Docker)
 *   - ../terraform-localstack/main.tf   → mismo patrón lab pero con Terraform
 *   - ../../.jenkins/scripts/pulumi-preview.sh → Jenkins ejecuta `pulumi preview`
 *   - unclic/lib/hub-links.ts           → NEXT_PUBLIC_DEMO_PULUMI_URL apunta a Pulumi Cloud
 *
 * BACKENDS
 *   - Pulumi Cloud: `pulumi login` (token en CI como secret)
 *   - Local: `pulumi login --local` (sin cuenta cloud)
 *
 * LOCALSTACK (avanzado)
 *   Pulumi AWS puede apuntar a LocalStack configurando endpoints; la vía más
 *   estable suele ser Terraform+tflocal para emulación. Si usas AWS real de
 *   desarrollo, este stack crea recursos reales (coste mínimo bucket).
 *
 *   Documentación: https://docs.localstack.cloud/user-guide/integrations/pulumi/
 */

import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";

// Prefijo de bucket: incluimos el nombre del stack (dev, staging, prod) para
// unicidad global en AWS real.
const stack = pulumi.getStack();
const bucketName = `fastflow-pos-lab-${stack}`.toLowerCase();

// Instancia de bucket. En AWS provider v6 preferimos recursos explícitos.
const labBucket = new aws.s3.BucketV2("posLabBucket", {
  bucket: bucketName,
  tags: {
    Project: "fastflow-pos",
    ManagedBy: "pulumi",
    Stack: stack,
  },
});

// Exportamos el nombre para verlo en consola Pulumi y enlazar documentación demos.
export const labBucketNameOut = labBucket.bucket;
export const stackNameOut = stack;
