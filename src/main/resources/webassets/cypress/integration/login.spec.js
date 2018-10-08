/// <reference types="Cypress" />

describe('Login page', () => {
  beforeEach(() => {
    cy.visit('/login/login.html');
  });

  it('can log in admin', () => {
    cy.get('#username').type('admin');
    cy.get('#password').type('password');
    cy.get('#login').click();
  });
});
