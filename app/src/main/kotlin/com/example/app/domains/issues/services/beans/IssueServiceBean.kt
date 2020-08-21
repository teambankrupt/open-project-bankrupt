package com.example.app.domains.issues.services.beans

import com.example.app.domains.issues.models.entities.Issue
import com.example.app.domains.issues.repositories.IssueRepository
import com.example.app.domains.issues.services.IssueService
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class IssueServiceBean @Autowired constructor(
        private val issueRepository: IssueRepository
) : IssueService {

    override fun search(query: String, page: Int, size: Int): Page<Issue> {
        return this.issueRepository.search(query, PageAttr.getPageRequest(page, size))
    }

    override fun save(entity: Issue): Issue {
        return this.issueRepository.save(entity)
    }

    override fun find(id: Long): Optional<Issue> {
        return this.issueRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("Issue", id) }
            entity.isDeleted = true
            this.issueRepository.save(entity)
        }
        this.issueRepository.deleteById(id)
    }

}