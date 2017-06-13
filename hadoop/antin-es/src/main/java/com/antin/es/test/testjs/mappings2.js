var m = {
    "mappings": {
        "sehr_xman_ehr_0": {
            "properties": {
                "XMAN_ID": {
                    "type": "string"
                },
                "EVENT": {
                    "type": "string"
                },
                "CATALOG_CODE": {
                    "type": "string"
                },
                "SERIAL": {
                    "type": "integer"
                },
                "CONTENT": {
                    "type": "string"
                },
                "COMPRESSION": {
                    "type": "integer"
                },
                "ENCRYPTION": {
                    "type": "integer"
                },
                "STATUS": {
                    "type": "integer"
                },
                "VERSION": {
                    "type": "string"
                },
                "TITLE": {
                    "type": "string"
                },
                "COMMIT_TIME": {
                    "type": "date",
                    "format": "strict_date_optional_time||epoch_millis"
                },
                "ISTEMP": {
                    "type": "integer"
                },
                "XML": {
                    "type": "string",
                    "analyzer": "english"
                }
            }
        }
    }
}