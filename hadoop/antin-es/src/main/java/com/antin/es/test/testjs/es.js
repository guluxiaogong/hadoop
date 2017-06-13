var json = {
    "sehr_xman_ehr_a_json": {
        "mappings": {
            "article": {
                "properties": {
                    "CATALOG_CODE": {
                        "type": "string"
                    },
                    "COMMIT_TIME": {
                        "type": "date",
                        "format": "strict_date_optional_time||epoch_millis"
                    },
                    "COMPRESSION": {
                        "type": "string"
                    },
                    "CONTENT": {
                        "type": "string"
                    },
                    "ENCRYPTION": {
                        "type": "string"
                    },
                    "EVENT": {
                        "type": "string"
                    },
                    "ISTEMP": {
                        "type": "object"
                    },
                    "SERIAL": {
                        "type": "string"
                    },
                    "STATUS": {
                        "type": "string"
                    },
                    "TITLE": {
                        "type": "string"
                    },
                    "VERSION": {
                        "type": "string"
                    },
                    "XMAN_ID": {
                        "type": "string"
                    },
                    "XML": {
                        "properties": {
                            "@title": {
                                "type": "string"
                            },
                            "@version": {
                                "type": "string"
                            },
                            "CLINIC_MASTER": {
                                "properties": {
                                    "AGE": {
                                        "type": "string"
                                    },
                                    "CHARGE_TYPE": {
                                        "type": "string"
                                    },
                                    "CLINIC_CHARGE": {
                                        "type": "string"
                                    },
                                    "CLINIC_FEE": {
                                        "type": "string"
                                    },
                                    "CLINIC_LABEL": {
                                        "type": "string"
                                    },
                                    "CLINIC_TYPE": {
                                        "type": "string"
                                    },
                                    "FIRST_VISIT_INDICATOR": {
                                        "type": "string"
                                    },
                                    "IDENTITY": {
                                        "type": "string"
                                    },
                                    "MR_PROVIDE_INDICATOR": {
                                        "type": "string"
                                    },
                                    "NAME": {
                                        "type": "string"
                                    },
                                    "NAME_PHONETIC": {
                                        "type": "string"
                                    },
                                    "OPERATOR": {
                                        "type": "string"
                                    },
                                    "OTHER_FEE": {
                                        "type": "string"
                                    },
                                    "PATIENT_ID": {
                                        "type": "string"
                                    },
                                    "REGISTERING_DATE": {
                                        "type": "string"
                                    },
                                    "REGISTRATION_STATUS": {
                                        "type": "string"
                                    },
                                    "REGIST_FEE": {
                                        "type": "string"
                                    },
                                    "SERIAL_NO": {
                                        "type": "string"
                                    },
                                    "SEX": {
                                        "type": "string"
                                    },
                                    "VISIT_DATE": {
                                        "type": "string"
                                    },
                                    "VISIT_DEPT": {
                                        "type": "string"
                                    },
                                    "VISIT_NO": {
                                        "type": "string"
                                    },
                                    "VISIT_TIME_DESC": {
                                        "type": "string"
                                    }
                                }
                            },
                            "EXAM_ITEMSs": {
                                "properties": {
                                    "COSTS": {
                                        "type": "string"
                                    },
                                    "EXAM_ITEM": {
                                        "type": "string"
                                    },
                                    "EXAM_ITEM_CODE": {
                                        "type": "string"
                                    },
                                    "EXAM_ITEM_NO": {
                                        "type": "string"
                                    },
                                    "EXAM_NO": {
                                        "type": "string"
                                    }
                                }
                            },
                            "EXAM_MASTER": {
                                "properties": {
                                    "CHARGES": {
                                        "type": "string"
                                    },
                                    "CLIN_DIAG": {
                                        "type": "string"
                                    },
                                    "COSTS": {
                                        "type": "string"
                                    },
                                    "DATE_OF_BIRTH": {
                                        "type": "string"
                                    },
                                    "EXAM_CLASS": {
                                        "type": "string"
                                    },
                                    "EXAM_DATE_TIME": {
                                        "type": "string"
                                    },
                                    "EXAM_NO": {
                                        "type": "string"
                                    },
                                    "EXAM_SUB_CLASS": {
                                        "type": "string"
                                    },
                                    "LOCAL_ID_CLASS": {
                                        "type": "string"
                                    },
                                    "NAME": {
                                        "type": "string"
                                    },
                                    "PATIENT_ID": {
                                        "type": "string"
                                    },
                                    "PATIENT_LOCAL_ID": {
                                        "type": "string"
                                    },
                                    "PATIENT_SOURCE": {
                                        "type": "string"
                                    },
                                    "PERFORMED_BY": {
                                        "type": "string"
                                    },
                                    "REPORTER": {
                                        "type": "string"
                                    },
                                    "REPORT_DATE_TIME": {
                                        "type": "string"
                                    },
                                    "REQ_DATE_TIME": {
                                        "type": "string"
                                    },
                                    "REQ_DEPT": {
                                        "type": "string"
                                    },
                                    "REQ_PHYSICIAN": {
                                        "type": "string"
                                    },
                                    "RESULT_STATUS": {
                                        "type": "string"
                                    },
                                    "SCHEDULED_DATE_TIME": {
                                        "type": "string"
                                    },
                                    "SEX": {
                                        "type": "string"
                                    },
                                    "STUDY_UID": {
                                        "type": "string"
                                    },
                                    "TECHNICIAN": {
                                        "type": "string"
                                    },
                                    "VISIT_ID": {
                                        "type": "string"
                                    }
                                }
                            },
                            "EXAM_REPORTs": {
                                "properties": {
                                    "DESCRIPTION": {
                                        "type": "string"
                                    },
                                    "EXAM_NO": {
                                        "type": "string"
                                    },
                                    "IMPRESSION": {
                                        "type": "string"
                                    },
                                    "IS_ABNORMAL": {
                                        "type": "string"
                                    },
                                    "RECOMMENDATION": {
                                        "type": "string"
                                    }
                                }
                            },
                            "PAT_MASTER_INDEX": {
                                "properties": {
                                    "BIRTH_PLACE": {
                                        "type": "string"
                                    },
                                    "CEHR_PATIENT_ID": {
                                        "type": "string"
                                    },
                                    "CEHR_SSID": {
                                        "type": "string"
                                    },
                                    "CHARGE_TYPE": {
                                        "type": "string"
                                    },
                                    "CITIZENSHIP": {
                                        "type": "string"
                                    },
                                    "DATE_OF_BIRTH": {
                                        "type": "string"
                                    },
                                    "IDENTITY": {
                                        "type": "string"
                                    },
                                    "MAILING_ADDRESS": {
                                        "type": "string"
                                    },
                                    "NAME": {
                                        "type": "string"
                                    },
                                    "NAME_PHONETIC": {
                                        "type": "string"
                                    },
                                    "NATION": {
                                        "type": "string"
                                    },
                                    "PATIENT_ID": {
                                        "type": "string"
                                    },
                                    "SEX": {
                                        "type": "string"
                                    },
                                    "VIP_INDICATOR": {
                                        "type": "string"
                                    }
                                }
                            },
                            "age": {
                                "type": "string"
                            },
                            "dept": {
                                "properties": {
                                    "#text": {
                                        "type": "string"
                                    },
                                    "@id": {
                                        "type": "string"
                                    }
                                }
                            },
                            "diagnosis": {
                                "properties": {
                                    "item": {
                                        "properties": {
                                            "#text": {
                                                "type": "string"
                                            },
                                            "@icd": {
                                                "type": "string"
                                            },
                                            "@prop": {
                                                "type": "string"
                                            },
                                            "@result": {
                                                "type": "string"
                                            }
                                        }
                                    }
                                }
                            },
                            "doctor": {
                                "properties": {
                                    "#text": {
                                        "type": "string"
                                    },
                                    "@title": {
                                        "type": "string"
                                    }
                                }
                            },
                            "exm_doctor": {
                                "type": "string"
                            },
                            "group": {
                                "properties": {
                                    "@date": {
                                        "type": "date",
                                        "format": "strict_date_optional_time||epoch_millis"
                                    },
                                    "@freq": {
                                        "type": "string"
                                    },
                                    "@method": {
                                        "type": "string"
                                    },
                                    "medicine": {
                                        "properties": {
                                            "#text": {
                                                "type": "string"
                                            },
                                            "@code": {
                                                "type": "string"
                                            },
                                            "@dose_quantity": {
                                                "type": "string"
                                            },
                                            "@dose_unit": {
                                                "type": "string"
                                            },
                                            "@notes": {
                                                "type": "string"
                                            },
                                            "@spec": {
                                                "type": "string"
                                            },
                                            "@total_day": {
                                                "type": "string"
                                            },
                                            "@total_quantity": {
                                                "type": "string"
                                            },
                                            "@total_unit": {
                                                "type": "string"
                                            }
                                        }
                                    }
                                }
                            },
                            "indept": {
                                "properties": {
                                    "#text": {
                                        "type": "string"
                                    },
                                    "@bed": {
                                        "type": "string"
                                    },
                                    "@id": {
                                        "type": "string"
                                    },
                                    "@time": {
                                        "type": "string"
                                    }
                                }
                            },
                            "item": {
                                "properties": {
                                    "#text": {
                                        "type": "string"
                                    },
                                    "@code": {
                                        "type": "string"
                                    },
                                    "@date": {
                                        "type": "string"
                                    },
                                    "@notes": {
                                        "type": "string"
                                    },
                                    "@price": {
                                        "type": "string"
                                    },
                                    "@quantity": {
                                        "type": "string"
                                    },
                                    "@spec": {
                                        "type": "string"
                                    },
                                    "@total": {
                                        "type": "string"
                                    },
                                    "@unit": {
                                        "type": "string"
                                    }
                                }
                            },
                            "lis_name": {
                                "type": "string"
                            },
                            "outdept": {
                                "properties": {
                                    "#text": {
                                        "type": "string"
                                    },
                                    "@bed": {
                                        "type": "string"
                                    },
                                    "@id": {
                                        "type": "string"
                                    },
                                    "@time": {
                                        "type": "string"
                                    }
                                }
                            },
                            "outdiagnosis": {
                                "properties": {
                                    "item": {
                                        "properties": {
                                            "#text": {
                                                "type": "string"
                                            },
                                            "@icd": {
                                                "type": "string"
                                            },
                                            "@prop": {
                                                "type": "string"
                                            },
                                            "@result": {
                                                "type": "string"
                                            }
                                        }
                                    }
                                }
                            },
                            "patient_name": {
                                "type": "string"
                            },
                            "patient_type": {
                                "type": "string"
                            },
                            "reg": {
                                "type": "string"
                            },
                            "report": {
                                "properties": {
                                    "@code": {
                                        "type": "string"
                                    },
                                    "@name": {
                                        "type": "string"
                                    },
                                    "@prompt": {
                                        "type": "string"
                                    },
                                    "@refval": {
                                        "type": "string"
                                    },
                                    "@result_char": {
                                        "type": "string"
                                    },
                                    "@unit": {
                                        "type": "string"
                                    }
                                }
                            },
                            "report_date": {
                                "type": "string"
                            },
                            "residence": {
                                "properties": {
                                    "#text": {
                                        "type": "string"
                                    },
                                    "@serial": {
                                        "type": "string"
                                    }
                                }
                            },
                            "sand_date": {
                                "type": "string"
                            },
                            "sec": {
                                "type": "string"
                            },
                            "sex": {
                                "type": "string"
                            },
                            "title": {
                                "type": "string"
                            }
                        }
                    }
                }
            }
        }
    }
};