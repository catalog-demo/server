import React, { Component } from 'react';
import CatalogService from './CatalogService';
import './catalog.css';

export default class Catalog extends Component {
    constructor(props){
        super(props)

        this.state = {
            syntax: '',
            catalogs: [],
        }

        this.handleInputSyntax = this.handleInputSyntax.bind(this);
        this.searchBySyntax = this.searchBySyntax.bind(this);

    }

    handleInputSyntax = (event) => {
        this.setState({syntax: event.target.value})
    }

    searchBySyntax = (event) => {
        event.preventDefault();
        CatalogService.getCatalogs(this.state.syntax).then(res => {
            console.log(res.data);
            this.setState({catalogs: res.data})
        })
    }

    render() {
        return (
            <>
                <div className="container my-4">
                    <div class="form-group">
                        <label for="syntax-input">Syntax</label>
                        <input type="text" class="form-control" id="syntax-input" onChange={this.handleInputSyntax} aria-describedby="syntax" placeholder="Syntax" />
                        <small id="syntax" class="form-text text-muted">The syntax must be right syntax such as: 'description:airflow'</small>
                    </div>
                    <button class="btn btn-primary" onClick={this.searchBySyntax}>Search</button>
                    <h1 className="text-center">Catalog</h1>
                </div>
                <table class="table text-center">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col" className="col-1">STT</th>
                        <th scope="col" className="col-1">Result Type</th>
                        <th scope="col" className="col-1">Result Sub Type</th>
                        <th scope="col" className="col-3">Relative Resource Name</th>
                        <th scope="col" className="col-2">Linked Resource</th>
                        <th scope="col" className="col-1">Modify Time</th>
                        <th scope="col" className="col-1">Integrated System</th>
                        <th scope="col" className="col-2">Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.catalogs.map(
                        (catalog, index) =>
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td className="text-truncate">{catalog.searchResultType}</td>
                                <td className="text-truncate">{catalog.searchResultSubType}</td>
                                <td className="text-truncate">{catalog.relativeResourceName}</td>
                                <td className="text-truncate"><a href={catalog.linkedResource} target="_blank" rel="noreferrer" >Link</a></td>
                                <td className="text-truncate">{catalog.modifyTime}</td>
                                <td className="text-truncate">{catalog.integratedSystem}</td>
                                <td className="text-truncate">{catalog.description}</td>
                            </tr>
                        )
                    }
                    </tbody>
                </table>
            </>
        )
    }
}
