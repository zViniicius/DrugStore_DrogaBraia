import React, { useState } from 'react';
import { Container } from './style.js';
import { Button, Col, Form, Row } from 'react-bootstrap/';
import api from '../../../services/api';
import MaskDate from "../Utils/MaskDate";

function UserAdd() {
    const initialFormData = {
        nome: '',
        email: '',
        senha: '',
        confirmarSenha: '',
        cpf: '',
        dataNascimento: '',
        genero: '',
        cep: '',
        numero: '',
    };

    const [formData, setFormData] = useState(initialFormData);
    const [passwordError, setPasswordError] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [step, setStep] = useState(3);

    const handleInputChange = (event) => {
        const { name, value } = event.target;


        if (name === 'dataNascimento') {
            const dateValue = new Date(value);
            const formattedDate = dateValue.toLocaleDateString();
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: formattedDate,
            }));
        } else {
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: value,
            }));
        }

        console.log(formData);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (formData.senha !== formData.confirmarSenha) {
            setPasswordError('As senhas não correspondem');
        } else {
            setPasswordError('');
            api
                .post('/usuarios', formData)
                .then((response) => {
                    console.log(response.data);
                    setFormData(initialFormData);
                })
                .catch((error) => {
                    if (error.response && error.response.data) {
                        const { message } = error.response.data;
                        setErrorMessage(message);
                        console.log(message);
                    } else {
                        console.error('Erro ao enviar os dados:', error);
                    }
                });
        }
    };

    return (
        <Container>
            <div className='container'>
                <Form onSubmit={handleSubmit}>
                    <Row className='mb-3'>
                        <Form.Group as={Col} controlId='formBasicNome'>
                            <Form.Label>Nome</Form.Label>
                            <Form.Control
                                type='text'
                                name='nome'
                                placeholder='Nome'
                                value={formData.nome}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group as={Col} controlId='formBasicEmail'>
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type='email'
                                name='email'
                                placeholder='Email'
                                value={formData.email}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                    </Row>
                    <Row className='mb-3'>
                        <Form.Group as={Col} controlId='formBasicSenha'>
                            <Form.Label>Senha</Form.Label>
                            <Form.Control
                                type='password'
                                name='senha'
                                placeholder='Senha'
                                value={formData.senha}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group as={Col} controlId='formBasicConfirmarSenha'>
                            <Form.Label>Confirmar Senha</Form.Label>
                            <Form.Control
                                type='password'
                                name='confirmarSenha'
                                placeholder='Confirmar Senha'
                                value={formData.confirmarSenha}
                                onChange={handleInputChange}
                                onBlur={() => {
                                    if (formData.senha !== formData.confirmarSenha) {
                                        setPasswordError('As senhas não correspondem');
                                    } else {
                                        setPasswordError('');
                                    }
                                }}
                                isInvalid={passwordError !== ''}
                            />
                            <Form.Control.Feedback type='invalid'>
                                {passwordError}
                            </Form.Control.Feedback>
                        </Form.Group>
                    </Row>

                    {step > 1 && (
                        <Row className='mb-3'>
                            <Form.Group as={Col} controlId='formBasicCPF'>
                                <Form.Label>CPF</Form.Label>
                                <Form.Control
                                    type='text'
                                    name='cpf'
                                    placeholder='CPF'
                                    value={formData.cpf}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId='formBasicDataNascimento'>
                                <Form.Label>Data Nascimento</Form.Label>
                                <Form.Control
                                    type='date'
                                    name='dataNascimento'
                                    placeholder='Data Nascimento'
                                    value={MaskDate(formData.dataNascimento)}
                                    onChange={handleInputChange.toLocaleString().split('T')[0].split('-').reverse().join('/')}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId='formBasicGenero'>
                                <Form.Label>Gênero</Form.Label>
                                <Form.Select
                                    aria-label='Default select example'
                                    name='genero'
                                    value={formData.genero}
                                    onChange={handleInputChange}
                                >
                                    <option disabled defaultValue>
                                        Selecione
                                    </option>
                                    <option value='M'>Masculino</option>
                                    <option value='F'>Feminino</option>
                                    <option value='N'>Não-Binário</option>
                                </Form.Select>
                            </Form.Group>
                        </Row>
                    )}

                    {step > 2 && (
                        <Row className='mb-3'>
                            <Form.Group as={Col} controlId='formBasicCEP'>
                                <Form.Label>CEP</Form.Label>
                                <Form.Control
                                    type='text'
                                    name='cep'
                                    placeholder='CEP'
                                    value={formData.cep}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId='formBasicNumero'>
                                <Form.Label>Numero</Form.Label>
                                <Form.Control
                                    type='text'
                                    name='numero'
                                    placeholder='Numero'
                                    value={formData.numero}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>
                        </Row>
                    )}

                    {errorMessage && (
                        <div className='error-message'>{errorMessage}</div>
                    )}
                    <Button variant='primary' type='submit'>
                        Submit
                    </Button>
                </Form>
            </div>
        </Container>
    );
}

export default UserAdd;
