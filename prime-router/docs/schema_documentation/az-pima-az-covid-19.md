
### Schema:         az/pima-az-covid-19
#### Description:   Pima County, AZ COVID-19 flat file

---

**Name**: Patient_last_name

**Type**: PERSON_NAME

**HL7 Field**: PID-5-1

**Cardinality**: [1..1]

**Documentation**:

The patient's last name

---

**Name**: Patient_first_name

**Type**: PERSON_NAME

**HL7 Field**: PID-5-2

**Cardinality**: [0..1]

**Documentation**:

The patient's first name

---

**Name**: Patient_middle_name

**Type**: PERSON_NAME

**HL7 Field**: PID-5-3

**Cardinality**: [0..1]

---

**Name**: Patient_suffix

**Type**: PERSON_NAME

**HL7 Field**: PID-5-4

**Cardinality**: [0..1]

---

**Name**: Patient_ID

**Type**: TEXT

**HL7 Field**: PID-3-1

**Cardinality**: [0..1]

---

**Name**: Ordered_test_name

**Type**: TABLE

**HL7 Field**: OBX-3-2

**Cardinality**: [0..1]

**Table**: LIVD-2020-11-18

**Table Column**: LOINC Long Name

**Documentation**:

The LOINC description of the test performed as related to the LOINC code.

---

**Name**: Specimen_source_site

**Type**: CODE

**Format**: $display

**HL7 Field**: SPM-8

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
119297000|Blood specimen (specimen)
71836000|Nasopharyngeal structure (body structure)
45206002|Nasal structure (body structure)

---

**Name**: Specimen_type_code

**Type**: CODE

**Format**: $display

**HL7 Field**: SPM-4

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
258500001|Nasopharyngeal swab
871810001|Mid-turbinate nasal swab
697989009|Anterior nares swab
258411007|Nasopharyngeal aspirate
429931000124105|Nasal aspirate
258529004|Throat swab
119334006|Sputum specimen
119342007|Saliva specimen
258607008|Bronchoalveolar lavage fluid sample
119364003|Serum specimen
119361006|Plasma specimen
440500007|Dried blood spot specimen
258580003|Whole blood sample
122555007|Venous blood specimen

**Documentation**:

The specimen source, such as Blood or Serum

---

**Name**: Device_ID

**Type**: TABLE

**Cardinality**: [0..1]


**Reference URL**:
[https://confluence.hl7.org/display/OO/Proposed+HHS+ELR+Submission+Guidance+using+HL7+v2+Messages#ProposedHHSELRSubmissionGuidanceusingHL7v2Messages-DeviceIdentification](https://confluence.hl7.org/display/OO/Proposed+HHS+ELR+Submission+Guidance+using+HL7+v2+Messages#ProposedHHSELRSubmissionGuidanceusingHL7v2Messages-DeviceIdentification) 

**Table**: LIVD-2020-11-18

**Table Column**: Model

---

**Name**: Device_type

**Type**: CODE

**Format**: $alt

**Cardinality**: [0..1]

**Alt Value Sets**

Code | Display
---- | -------
BD Veritor System for Rapid Detection of SARS-CoV-2*|Antigen
ID Now|Molecular
BinaxNOW COVID-19 Ag Card|Antigen
LumiraDx SARS-CoV-2 Ag Test*|Antigen
Sofia 2 SARS Antigen FIA|Antigen

**Documentation**:

Additional field per request from the County.  HHS lists 4 codes for 'Prior test' question that may match whats desired here:  Molecular, Antigen, Antibody/Serology, and UNK.

---

**Name**: Instrument_ID

**Type**: ID

**Cardinality**: [0..1]

---

**Name**: Testing_lab_specimen_ID

**Type**: ID

**HL7 Field**: SPM-2-1

**Cardinality**: [0..1]

---

**Name**: Test_result_code

**Type**: CODE

**Format**: $display

**HL7 Field**: OBX-5

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
260373001|Detected
260415000|Not detected
720735008|Presumptive positive
10828004|Positive
42425007|Equivocal
260385009|Negative
895231008|Not detected in pooled specimen
462371000124108|Detected in pooled specimen
419984006|Inconclusive
125154007|Specimen unsatisfactory for evaluation
455371000124106|Invalid result

**Documentation**:

The result of the test performed. For IgG, IgM and CT results that give a numeric value put that here.

---

**Name**: Illness_onset_date

**Type**: DATE

**Format**: yyyy-MM-dd

**HL7 Field**: AOE

**Cardinality**: [0..1]

---

**Name**: Specimen_collection_date_time

**Type**: DATETIME

**Format**: yyyy-MM-dd

**HL7 Field**: SPM-17-1

**Cardinality**: [0..1]

**Documentation**:

The date which the specimen was collected. The default format is yyyyMMddHHmmsszz


---

**Name**: Order_test_date

**Type**: DATE

**Format**: yyyy-MM-dd

**HL7 Field**: ORC-15

**Cardinality**: [0..1]

---

**Name**: Test_date

**Type**: DATETIME

**Format**: yyyy-MM-dd

**HL7 Field**: OBX-19

**Cardinality**: [0..1]

---

**Name**: Date_result_released

**Type**: DATETIME

**Format**: yyyy-MM-dd

**HL7 Field**: OBR-22

**Cardinality**: [0..1]

---

**Name**: Patient_race

**Type**: CODE

**Format**: $display

**HL7 Field**: PID-10

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
1002-5|American Indian or Alaska Native
2028-9|Asian
2054-5|Black or African American
2076-8|Native Hawaiian or Other Pacific Islander
2106-3|White
2131-1|Other
UNK|Unknown
ASKU|Asked, but unknown

**Documentation**:

The patient's race. There is a common valueset defined for race values, but some states may choose to define different code/value combinations.


---

**Name**: Patient_DOB

**Type**: DATE

**Format**: yyyy-MM-dd

**HL7 Field**: PID-7

**Cardinality**: [0..1]

**Documentation**:

The patient's date of birth. Default format is yyyyMMdd.

Other states may choose to define their own formats.


---

**Name**: Patient_gender

**Type**: CODE

**HL7 Field**: PID-8-1

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
M|Male
F|Female
O|Other
A|Ambiguous
U|Unknown
N|Not applicable

**Documentation**:

The patient's gender. There is a valueset defined based on the values in PID-8-1, but downstream consumers are free to define their own accepted values. Please refer to the consumer-specific schema if you have questions.


---

**Name**: Patient_ethnicity

**Type**: CODE

**Format**: $display

**HL7 Field**: PID-22

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
H|Hispanic or Latino
N|Non Hispanic or Latino
U|Unknown

**Documentation**:

The patient's ethnicity. There is a valueset defined based on the values in PID-22, but downstream consumers are free to define their own values. Please refer to the consumer-specific schema if you have questions.


---

**Name**: Patient_street

**Type**: STREET

**HL7 Field**: PID-11-1

**Cardinality**: [0..1]

**Documentation**:

The patient's street address

---

**Name**: Patient_street_2

**Type**: STREET_OR_BLANK

**HL7 Field**: PID-11-2

**Cardinality**: [0..1]

**Documentation**:

The patient's second address line

---

**Name**: Patient_city

**Type**: CITY

**HL7 Field**: PID-11-3

**Cardinality**: [0..1]

**Documentation**:

The patient's city

---

**Name**: Patient_state

**Type**: TABLE

**HL7 Field**: PID-11-4

**Cardinality**: [1..1]

**Table**: fips-county

**Table Column**: State

**Documentation**:

The patient's state

---

**Name**: Patient_zip_code

**Type**: POSTAL_CODE

**HL7 Field**: PID-11-5

**Cardinality**: [0..1]

**Documentation**:

The patient's zip code

---

**Name**: Patient_phone_number

**Type**: TELEPHONE

**HL7 Field**: PID-13

**Cardinality**: [0..1]

**Documentation**:

The patient's phone number with area code

---

**Name**: Patient_county

**Type**: TABLE_OR_BLANK

**Cardinality**: [1..1]

**Table**: fips-county

**Table Column**: County

---

**Name**: Patient_role

**Type**: TEXT

**Cardinality**: [0..1]

---

**Name**: Employed_in_healthcare

**Type**: CODE

**HL7 Field**: AOE

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
Y|Yes
N|No
UNK|Unknown

**Documentation**:

Is the patient employed in health care?

---

**Name**: Resident_congregate_setting

**Type**: CODE

**HL7 Field**: AOE

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
Y|Yes
N|No
UNK|Unknown

**Documentation**:

Does the patient reside in a congregate care setting?

---

**Name**: First_test

**Type**: CODE

**HL7 Field**: AOE

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
Y|Yes
N|No
UNK|Unknown

**Documentation**:

Is this the patient's first test for this condition?

---

**Name**: Symptomatic_for_disease

**Type**: CODE

**HL7 Field**: AOE

**Cardinality**: [0..1]

**Value Sets**

Code | Display
---- | -------
Y|Yes
N|No
UNK|Unknown

**Documentation**:

Is the patient symptomatic?

---

**Name**: Testing_lab_name

**Type**: TEXT

**Cardinality**: [0..1]

---

**Name**: Testing_lab_CLIA

**Type**: ID_CLIA

**HL7 Field**: OBX-23-10

**Cardinality**: [1..1]

**Documentation**:

CLIA Number from the laboratory that sends the message to DOH

An example of the ID is 03D2159846


---

**Name**: Testing_lab_street

**Type**: STREET

**HL7 Field**: OBX-24-1

**Cardinality**: [0..1]

---

**Name**: Testing_lab_street_2

**Type**: STREET_OR_BLANK

**HL7 Field**: OBX-24-2

**Cardinality**: [0..1]

---

**Name**: Testing_lab_city

**Type**: CITY

**HL7 Field**: OBX-24-3

**Cardinality**: [0..1]

---

**Name**: Testing_lab_state

**Type**: TABLE

**HL7 Field**: OBX-24-4

**Cardinality**: [0..1]

**Table**: fips-county

**Table Column**: State

---

**Name**: Testing_lab_zip_code

**Type**: POSTAL_CODE

**HL7 Field**: OBX-24-5

**Cardinality**: [0..1]

---

**Name**: Testing_lab_phone_number

**Type**: TELEPHONE

**Cardinality**: [0..1]

---

**Name**: Testing_lab_county

**Type**: TABLE

**Cardinality**: [0..1]

**Table**: fips-county

**Table Column**: County

---

**Name**: Ordering_facility_name

**Type**: TEXT

**Cardinality**: [0..1]

---

**Name**: Ordering_facility_street

**Type**: STREET

**HL7 Field**: ORC-22-1

**Cardinality**: [0..1]

**Documentation**:

The address of the facility which the test was ordered from

---

**Name**: Ordering_facility_street_2

**Type**: STREET_OR_BLANK

**HL7 Field**: ORC-22-2

**Cardinality**: [0..1]

**Documentation**:

The secondary address of the facility which the test was ordered from

---

**Name**: Ordering_facility_city

**Type**: CITY

**HL7 Field**: ORC-22-3

**Cardinality**: [0..1]

**Documentation**:

The city of the facility which the test was ordered from

---

**Name**: Ordering_facility_state

**Type**: TABLE

**HL7 Field**: ORC-22-4

**Cardinality**: [1..1]

**Table**: fips-county

**Table Column**: State

**Documentation**:

The state of the facility which the test was ordered from

---

**Name**: Ordering_facility_zip_code

**Type**: POSTAL_CODE

**HL7 Field**: ORC-22-5

**Cardinality**: [0..1]

**Documentation**:

The zip code of the facility which the test was ordered from

---

**Name**: Ordering_facility_phone_number

**Type**: TELEPHONE

**HL7 Field**: ORC-23

**Cardinality**: [1..1]

**Documentation**:

The phone number of the facility which the test was ordered from

---

**Name**: Ordering_facility_county

**Type**: TABLE

**Cardinality**: [0..1]

**Table**: fips-county

**Table Column**: County

---

**Name**: Ordering_provider_ID

**Type**: ID_NPI

**HL7 Field**: ORC-12-1

**Cardinality**: [0..1]

**Documentation**:

The ordering provider’s National Provider Identifier

---

**Name**: Ordering_provider_last_name

**Type**: PERSON_NAME

**Cardinality**: [0..1]

**Documentation**:

The last name of provider who ordered the test

---

**Name**: Ordering_provider_first_name

**Type**: PERSON_NAME

**HL7 Field**: ORC-12-3

**Cardinality**: [0..1]

**Documentation**:

The first name of the provider who ordered the test

---

**Name**: Ordering_provider_street

**Type**: STREET

**HL7 Field**: ORC-24-1

**Cardinality**: [0..1]

**Documentation**:

The street address of the provider

---

**Name**: Ordering_provider_street_2

**Type**: STREET_OR_BLANK

**HL7 Field**: ORC-24-2

**Cardinality**: [0..1]

**Documentation**:

The street second address of the provider

---

**Name**: Ordering_provider_city

**Type**: CITY

**HL7 Field**: ORC-24-3

**Cardinality**: [0..1]

**Documentation**:

The city of the provider

---

**Name**: Ordering_provider_state

**Type**: TABLE

**HL7 Field**: ORC-24-4

**Cardinality**: [0..1]

**Table**: fips-county

**Table Column**: State

**Documentation**:

The state of the provider

---

**Name**: Ordering_provider_zip_code

**Type**: POSTAL_CODE

**HL7 Field**: ORC-24-5

**Cardinality**: [0..1]

**Documentation**:

The zip code of the provider

---

**Name**: Ordering_provider_phone_number

**Type**: TELEPHONE

**HL7 Field**: ORC-14

**Cardinality**: [0..1]

**Documentation**:

The phone number of the provider

---

**Name**: Ordering_provider_county

**Type**: TABLE

**Cardinality**: [0..1]

**Table**: fips-county

**Table Column**: County

---
