import { Resource } from "sst";

export async function handler() {
  return {
    statusCode: 200,
    body: JSON.stringify({
      message: "Hello from FastFlow SST example",
      bucket: Resource.MyBucket?.name ?? "not linked",
    }),
  };
}
