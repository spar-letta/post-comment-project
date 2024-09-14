package com.javenock.project.docs;

public class Examples {
    public static final String GET_PARENT_COMMENT_OK_RESPONSE = "{\n" +
            "    \"totalElements\": 1,\n" +
            "    \"pageSize\": 1,\n" +
            "    \"totalPages\": 1,\n" +
            "    \"last\": true,\n" +
            "    \"first\": true,\n" +
            "    \"pageNumber\": 0,\n" +
            "    \"content\": [\n" +
            "        {\n" +
            "            \"publicId\": \"2fa24c66-f226-4ccb-ba32-797d751be7a4\",\n" +
            "            \"dateCreated\": \"2024-09-13 23:30:40\",\n" +
            "            \"createdBy\": {\n" +
            "                \"publicId\": \"bb874ce2-dc46-4f11-8915-c1d644f236df\",\n" +
            "                \"firstName\": \"peterKason\"\n" +
            "            },\n" +
            "            \"commentBody\": \"comment about hetel 5 star\",\n" +
            "            \"childrenComment\": [\n" +
            "                {\n" +
            "                    \"publicId\": \"20ecd316-586b-4ed6-b886-0cd3fd423cd4\",\n" +
            "                    \"dateCreated\": \"2024-09-13 23:30:40\",\n" +
            "                    \"createdBy\": {\n" +
            "                        \"publicId\": \"bb874ce2-dc46-4f11-8915-c1d644f236df\",\n" +
            "                        \"firstName\": \"peterKason\"\n" +
            "                    },\n" +
            "                    \"commentBody\": \"Am a child comment\",\n" +
            "                    \"childrenComment\": [\n" +
            "                        \n" +
            "                    ],\n" +
            "                    \"type\": \"CHILD\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"publicId\": \"220a8c9e-85fe-47a8-9dd4-450c290bb5be\",\n" +
            "                    \"dateCreated\": \"2024-09-13 23:30:41\",\n" +
            "                    \"createdBy\": {\n" +
            "                        \"publicId\": \"bb874ce2-dc46-4f11-8915-c1d644f236df\",\n" +
            "                        \"firstName\": \"peterKason\"\n" +
            "                    },\n" +
            "                    \"commentBody\": \"Am a child comment 2\",\n" +
            "                    \"childrenComment\": [\n" +
            "                        \n" +
            "                    ],\n" +
            "                    \"type\": \"CHILD\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"type\": \"PARENT\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
