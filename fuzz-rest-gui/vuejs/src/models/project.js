export class Project {
  constructor({ description = null, oasUrl = null } = {}) {
    this.description = description;
    this.oasUrl = oasUrl;
  }
}

export function createProject(project) {
  const description = project.description;
  const oasUrl = project.oasUrl;

  return Object.freeze(
    new Project(
      {description, oasUrl}
    )
  );
}