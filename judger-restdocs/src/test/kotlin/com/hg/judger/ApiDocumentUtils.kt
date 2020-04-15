package com.hg.judger

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors.*


interface ApiDocumentUtils {
    companion object {
        val documentRequest: OperationRequestPreprocessor?
            get() = preprocessRequest(
                modifyUris() // (1)
                    .scheme("https")
                    .host("docs.api.com")
                    .removePort(),
                prettyPrint()
            )

        val documentResponse: OperationResponsePreprocessor?
            get() = preprocessResponse(prettyPrint())
    }
}