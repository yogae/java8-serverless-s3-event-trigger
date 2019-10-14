# serverless java gradle

## 환경변수

- env.json 생성

```json
# env.json
{
    "BUCKET_NAME": "<bucket name>",
    "UPLOAD_PREFIX": "<s3 trigger prefix>" # uploads
}
```

## 사용법

```bash
# build
sh gradlew build

# serverless deploy
sls deploy
```

## upload file

```bash
aws s3 cp <file path> s3://<bucket name>/<file path>

# aws s3 cp test.txt s3://jihyun-upload-trigger-bucket/uploads/test.txt
```