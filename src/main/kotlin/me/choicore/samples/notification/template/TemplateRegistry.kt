package me.choicore.samples.notification.template

class TemplateRegistry(
    private val templateRepository: TemplateRepository,
) {
    fun register(definition: TemplateDefinition) {
        check(!existsByName(definition.name)) {
            "Template with name '${definition.name}' already exists"
        }

        templateRepository.register(definition)
    }

    fun getTemplate(name: String): Template = templateRepository.getTemplate(name)

    fun existsByName(name: String): Boolean = templateRepository.existsByName(name)
}
